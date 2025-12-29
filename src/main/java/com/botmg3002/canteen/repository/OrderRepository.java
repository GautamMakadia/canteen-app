package com.botmg3002.canteen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.botmg3002.canteen.model.Order;
import com.botmg3002.canteen.model.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE DATE(o.createdAt) = CURRENT_DATE")
    List<Order> findAllByToday();

    @Query(value = "select o from Order o where o.createdAt >= :lastMonth AND o.customer.id = :customerId")
    List<Order> findHistoryByCustomer(@Param("lastMonth") LocalDateTime lastMonth,
            @Param("customerId") Long customerId);

    default List<Order> findTodayOrdersByCanteenId(Long canteenId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return findByCanteenIdAndCreatedAtBetween(canteenId, startOfDay, endOfDay);
    }

    List<Order> findByCanteenIdAndCreatedAtBetween(Long canteenId, LocalDateTime start, LocalDateTime end);

    default Stream<Order> findTodayOrderByCustomerId(Long customerId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return findByCustomerIdAndCreatedAtBetween(customerId, startOfDay, endOfDay).stream();
    }

    default Stream<Order> findTodayByOrderStatus(Long canteenId, OrderStatus status) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);


        return findByCanteenIdAndStatusAndCreatedAtBetween(canteenId, status, startOfDay, endOfDay);
    }

    Stream<Order> findByCanteenIdAndStatusAndCreatedAtBetween(Long canteenId, OrderStatus status, LocalDateTime start, LocalDateTime end);

    List<Order> findByCustomerIdAndCreatedAtBetween(Long customerId, LocalDateTime start, LocalDateTime end);
}
package com.botmg3002.canteen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.botmg3002.canteen.model.Customer;
import com.botmg3002.canteen.model.Order;

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

    default Stream<Order> findTodayOrderByCustomer(Customer customer) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return findByCustomerAndCreatedAtBetween(customer, startOfDay, endOfDay).stream();
    }

    List<Order> findByCustomerAndCreatedAtBetween(Customer customer, LocalDateTime start, LocalDateTime end);
}

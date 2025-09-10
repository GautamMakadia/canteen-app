package com.botmg3002.canteen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.botmg3002.canteen.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE DATE(o.createdAt) = CURRENT_DATE")
    List<Order> findAllByToday();

    @Query(value = "select o from Order o where o.createdAt >= :lastMonth AND o.customer.id = :customerId")
    List<Order> findHistoryByCustomer(@Param("lastMonth") LocalDateTime lastMonth, @Param("customerId") Long customerId);
}

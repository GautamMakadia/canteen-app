package com.botmg3002.canteen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.botmg3002.canteen.model.CartItem;
import com.botmg3002.canteen.model.CartItemKey;

public interface CartItemRepository extends JpaRepository<CartItem,CartItemKey> {

    @Query("""
        SELECT c FROM  CartItem c 
        WHERE c.customer.id = :customerId AND c.item.id = :itemId
        """)
    Optional<CartItem> findByCustomerIdAndItemId(@Param("customerId")Long customerId, @Param("itemId") Long itemId);
}

package com.botmg3002.canteen.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.botmg3002.canteen.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Transactional
    void deleteByCustomerIdAndItemIdIn(Long customerId, Set<Long> ids);

    List<CartItem> findByCustomerIdAndItemCanteenId(Long customerId, Long canteenId);

    @Query("""
                SELECT c
                FROM CartItem c
                WHERE c.customer.id = :customerId
                  AND c.item.id = :itemId
                  AND (
                        (:subItemId IS NULL AND c.subItemType.id IS NULL)
                        OR (:subItemId IS NOT NULL AND c.subItemType.id = :subItemId)
                      )
            """)
    Optional<CartItem> findCartItem(
            @Param("customerId") Long customerId,
            @Param("itemId") Long itemId,
            @Param("subItemId") Long subItemId);

    Optional<CartItem> findByIdAndCustomerId(Long id, Long customerId);

    Set<CartItem> findByCustomerIdAndIdIn(Long id, Set<Long> cartItemIds);

}

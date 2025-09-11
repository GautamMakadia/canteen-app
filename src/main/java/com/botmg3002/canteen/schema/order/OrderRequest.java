package com.botmg3002.canteen.schema.order;

import java.util.HashSet;
import java.util.Set;

import com.botmg3002.canteen.model.OrderStatus;


public class OrderRequest {
    private Long canteenId;
    private Long customerId;
    private int itemCount;
    private OrderStatus status;
    private Set<Long> cartItemIds = new HashSet<>();

    public Long getCanteenId() {
        return canteenId;
    }
    public void setCanteenId(Long canteenId) {
        this.canteenId = canteenId;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public int getItemCount() {
        return itemCount;
    }
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public Set<Long> getCartItemIds() {
        return cartItemIds;
    }
    public void setCartItemIds(Set<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }
    
    
}
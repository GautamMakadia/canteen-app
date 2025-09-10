package com.botmg3002.canteen.schema.order;

import java.util.ArrayList;
import java.util.List;

import com.botmg3002.canteen.model.OrderStatus;


public class OrderRequest {
    private Long canteenId;
    private Long customerId;
    private int itemCount;
    private int total;
    private OrderStatus status;
    private List<OrderItemRequest> orderItems = new ArrayList<>();

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
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
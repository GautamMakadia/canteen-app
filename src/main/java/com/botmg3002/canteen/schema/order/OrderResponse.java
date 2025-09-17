package com.botmg3002.canteen.schema.order;

import java.util.List;

import com.botmg3002.canteen.schema.canteen.CanteenResponse;
import com.botmg3002.canteen.schema.customer.CustomerResponse;


public class OrderResponse {
    private Long id;
    private Long customerId;
    private Long canteenId;
    private int total;
    private int itemCount;
    private String status;
    private List<OrderItemResponse> orderItems;
    private CustomerResponse customer;
    private CanteenResponse canteen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(Long canteenId) {
        this.canteenId = canteenId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemResponse> orderItems) {
        this.orderItems = orderItems;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    public CanteenResponse getCanteen() {
        return canteen;
    }

    public void setCanteen(CanteenResponse canteen) {
        this.canteen = canteen;
    }
}

package com.botmg3002.canteen.schema.order;

import com.botmg3002.canteen.schema.Item.SubItemTypeResponse;

public class OrderItemResponse {
    private Long id;
    private Long itemId;
    private Long orderId;
    private String name;
    private Integer price;
    private SubItemTypeResponse subItemType;
    private int quantity;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public SubItemTypeResponse getSubItemType() {
        return subItemType;
    }
    public void setSubItemType(SubItemTypeResponse subItemType) {
        this.subItemType = subItemType;
    }
    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    

    
}

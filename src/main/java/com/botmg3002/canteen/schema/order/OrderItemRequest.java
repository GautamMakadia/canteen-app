package com.botmg3002.canteen.schema.order;


public class OrderItemRequest {
    private Long itemId;
    private Long subItemTypeId;
    private int quantity;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getSubItemTypeId() {
        return subItemTypeId;
    }

    public void setSubItemTypeId(Long subItemTypeId) {
        this.subItemTypeId = subItemTypeId;
    }
}

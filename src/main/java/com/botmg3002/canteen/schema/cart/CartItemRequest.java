package com.botmg3002.canteen.schema.cart;

public class CartItemRequest {
    private Long itemId;
    private Long subItemTypeId;
    private Integer quantity;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity == null ? 1 : quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity == null ? 1 : quantity;
    }

    public Long getSubItemTypeId() {
        return subItemTypeId;
    }

    public void setSubItemTypeId(Long subItemTypeId) {
        this.subItemTypeId = subItemTypeId;
    }

}

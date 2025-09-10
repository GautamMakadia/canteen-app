package com.botmg3002.canteen.schema.cart;

public class CartItemRequest {
    private Long itemId;
    private int quantity = 1;
    
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

    

    

}

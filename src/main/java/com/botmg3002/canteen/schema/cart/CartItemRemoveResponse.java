package com.botmg3002.canteen.schema.cart;

public class CartItemRemoveResponse {
    String message;
    Integer quantity;

    public CartItemRemoveResponse(String message, Integer quantity) {
        this.message = message;
        this.quantity = quantity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}

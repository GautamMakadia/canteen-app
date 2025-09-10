package com.botmg3002.canteen.schema.cart;

import com.botmg3002.canteen.schema.Item.ItemResponse;

public class CartItemResponse {
    private ItemResponse item;
    private int quantity;


    public CartItemResponse(ItemResponse item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
    public ItemResponse getItem() {
        return item;
    }
    public void setItem(ItemResponse item) {
        this.item = item;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

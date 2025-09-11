package com.botmg3002.canteen.schema.cart;

import com.botmg3002.canteen.schema.Item.ItemResponse;
import com.botmg3002.canteen.schema.Item.SubItemTypeResponse;

public class CartItemResponse {
    private Long id;
    private ItemResponse item;
    private SubItemTypeResponse subItemType;
    private int quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(Long id, ItemResponse item, SubItemTypeResponse subItemType, int quantity) {
        this.id = id;
        this.item = item;
        this.subItemType = subItemType;
        this.quantity = quantity;
    }

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

    public SubItemTypeResponse getSubItemType() {
        return subItemType;
    }

    public void setSubItemType(SubItemTypeResponse subItemType) {
        this.subItemType = subItemType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

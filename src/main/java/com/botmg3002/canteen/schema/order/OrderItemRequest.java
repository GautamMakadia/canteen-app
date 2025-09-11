package com.botmg3002.canteen.schema.order;


public class OrderItemRequest {
    private Long itemId;
    private Long subItemTypeId;


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSubItemTypeId() {
        return subItemTypeId;
    }

    public void setSubItemTypeId(Long subItemTypeId) {
        this.subItemTypeId = subItemTypeId;
    }
}

package com.botmg3002.canteen.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CartItemKey implements Serializable {

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "sub_item_type_id")
    private Long subItemTypeId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        CartItemKey that = (CartItemKey) obj;
        return Objects.equals(customerId, that.getCustomerId()) &&
                Objects.equals(itemId, that.getItemId()) &&
                Objects.equals(subItemTypeId, that.getSubItemTypeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, itemId, subItemTypeId);
    }

}

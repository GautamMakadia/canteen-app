package com.botmg3002.canteen.model;

import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Check(constraints = "quantity > 0")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subItemTypeId")
    private SubItemType subItemType;

    @Column(nullable = false)
    private Integer quantity = 1;

    public OrderItem() {
    }

    public OrderItem(Long id, Item item, int quantity) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
    }

    public OrderItem(Order order, Item item, SubItemType subItemType, int quantity) {
        this.order = order;
        this.item = item;
        this.subItemType = subItemType;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public SubItemType getSubItemType() {
        return subItemType;
    }

    public void setSubItemType(SubItemType subItemType) {
        this.subItemType = subItemType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}

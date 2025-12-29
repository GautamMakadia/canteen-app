package com.botmg3002.canteen.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;

    @ManyToOne(optional = true)
    @JoinColumn(name = "subItemTypeId", nullable = true)
    private SubItemType subItemType;

    @Column(nullable = false)
    private Integer quantity;

    public CartItem() {

    }

    public CartItem(Long id) {
        this.id = id;
    }

    public CartItem(Long id, Customer customer, Item item, SubItemType subItemType, Integer quantity) {
        this.id = id;
        this.customer = customer;
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


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    

    public void incrementQuantity() {
        this.quantity = (this.quantity == null ? 1 : this.quantity + 1);
    }

    public void decrementQuantity() {
        if (this.quantity == 1)
            return;

        quantity--;
    }

    public SubItemType getSubItemType() {
        return subItemType;
    }

    public void setSubItemType(SubItemType subItemType) {
        this.subItemType = subItemType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

package com.botmg3002.canteen.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @EmbeddedId
    private CartItemKey id;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private int quantity = 1;


    public CartItem() {
        
    }

    public CartItem(CartItemKey id) {
        this.id = id;
    }

    

    public CartItem(Customer customer, Item item) {
        this.customer = customer;
        this.item = item;
    }



    public CartItemKey getId() {
        return id;
    }

    public void setId(CartItemKey id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuntity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }
}

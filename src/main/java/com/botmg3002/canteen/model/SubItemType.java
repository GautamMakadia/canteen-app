package com.botmg3002.canteen.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SubItemType {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer extraPrice;

    public SubItemType(){}

    public SubItemType(Long id, String name, Integer extraPrice, Item item) {
        this.id = id;
        this.name = name;
        this.extraPrice = extraPrice;
        this.item = item;
    }

    public SubItemType(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false, updatable = false)
    private Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Integer extraPrice) {
        this.extraPrice = extraPrice;
    }
    
}

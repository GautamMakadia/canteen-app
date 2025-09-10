package com.botmg3002.canteen.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Check;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Check(constraints = "price > 0")
public class Item implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    private String imageUrl;
    
    @Column(nullable = false)
    private Integer price;
    
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubItemType> subItemTypes = new HashSet<>();

    @Column(nullable = false)
    private Boolean isAvailable = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "canteenId")
    private Canteen canteen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId") 
    private Category category;

    public Item(){}
    public Item(Long id) {
        this.id = id;
    }
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Canteen getCanteen() {
        return canteen;
    }

    public void setCanteen(Canteen canteen) {
        this.canteen = canteen;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<SubItemType> getSubItemTypes() {
        return subItemTypes;
    }

    public void setSubItemTypes(Set<SubItemType> subItemTypes) {
        this.subItemTypes = subItemTypes;
    }

    public boolean addSubItemType(SubItemType subItemType) {
        return this.subItemTypes.add(subItemType);
    }
    
}

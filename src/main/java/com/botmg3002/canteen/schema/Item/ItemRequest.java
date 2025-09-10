package com.botmg3002.canteen.schema.Item;

import java.util.HashSet;
import java.util.Set;


public class ItemRequest {
    private String name;
    private String imageUrl;
    private int price;
    private Long canteenId;
    private Long categoryId;
    private Set<SubItemTypeRequest> subItemTypes = new HashSet<>();
    private Boolean isAvailable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(Long canteenId) {
        this.canteenId = canteenId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Set<SubItemTypeRequest> getSubItemTypes() {
        return subItemTypes;
    }

    public void setSubItemTypes(Set<SubItemTypeRequest> subItemTypes) {
        this.subItemTypes = subItemTypes;
    }

    

}

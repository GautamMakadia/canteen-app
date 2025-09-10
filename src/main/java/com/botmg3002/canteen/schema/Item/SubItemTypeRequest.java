package com.botmg3002.canteen.schema.Item;


public class SubItemTypeRequest {
   
    private String name;
    
    private Integer extraPrice;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(int extraPrice) {
        this.extraPrice = extraPrice;
    }
    
}

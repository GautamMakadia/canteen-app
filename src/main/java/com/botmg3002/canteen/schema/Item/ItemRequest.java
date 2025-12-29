package com.botmg3002.canteen.schema.Item;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private String name;
    private String imageUrl;
    private int price;
    private Long canteenId;
    private Long categoryId;
    private Set<SubItemTypeRequest> subItemTypes = new HashSet<>();
    private Boolean isAvailable; 

}

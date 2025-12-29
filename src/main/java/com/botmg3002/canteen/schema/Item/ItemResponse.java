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
public class ItemResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private Integer price;
    private Long canteenId;
    private Long categoryId;
    private Set<SubItemTypeResponse> subItemTypes = new HashSet<>();
    private Boolean isAvailable;
}

package com.botmg3002.canteen.schema.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubItemTypeResponse {
    private Long id;
    private String name;
    private Integer extraPrice;
    private Long itemId;
}

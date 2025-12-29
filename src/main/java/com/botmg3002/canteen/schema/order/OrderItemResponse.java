package com.botmg3002.canteen.schema.order;

import com.botmg3002.canteen.schema.Item.SubItemTypeResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Long itemId;
    private Long orderId;
    private String name;
    private Integer price;
    private SubItemTypeResponse subItemType;
    private int quantity;

}

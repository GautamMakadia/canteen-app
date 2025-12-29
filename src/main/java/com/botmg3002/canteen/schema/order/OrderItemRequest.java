package com.botmg3002.canteen.schema.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    private Long itemId;
    private Long subItemTypeId;
}

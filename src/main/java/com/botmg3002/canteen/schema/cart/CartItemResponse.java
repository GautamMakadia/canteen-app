package com.botmg3002.canteen.schema.cart;

import com.botmg3002.canteen.schema.Item.ItemResponse;
import com.botmg3002.canteen.schema.Item.SubItemTypeResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    private ItemResponse item;
    private SubItemTypeResponse subItemType;
    private int quantity;
}

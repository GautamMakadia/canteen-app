package com.botmg3002.canteen.schema.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRemoveResponse {
    String message;
    Integer quantity;
}

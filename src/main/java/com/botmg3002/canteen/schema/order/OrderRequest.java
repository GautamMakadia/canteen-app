package com.botmg3002.canteen.schema.order;

import java.util.HashSet;
import java.util.Set;

import com.botmg3002.canteen.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long canteenId;
    private Long customerId;
    private int itemCount;
    private OrderStatus status;
    private Set<Long> cartItemIds = new HashSet<>();
}
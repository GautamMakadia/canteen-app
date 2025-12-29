package com.botmg3002.canteen.schema.order;

import java.util.List;

import com.botmg3002.canteen.schema.canteen.CanteenResponse;
import com.botmg3002.canteen.schema.customer.CustomerResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long customerId;
    private Long canteenId;
    private int total;
    private int itemCount;
    private String status;
    private List<OrderItemResponse> orderItems;
    private CustomerResponse customer;
    private CanteenResponse canteen;
}

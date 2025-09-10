package com.botmg3002.canteen.schema.order;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.botmg3002.canteen.model.Order;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { OrderItemMapper.class })
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "canteen.id", source = "canteenId")
    @Mapping(target = "customer.id", source = "customerId")
    Order toEntity(OrderRequest orderRequest);

    @Mapping(target = "canteenId", source = "canteen.id")
    @Mapping(target = "customerId", source = "customer.id")
    OrderResponse toResponse(Order order);

    @AfterMapping
    default void linkOrderItems(@MappingTarget Order order) {
        if (order == null) {
            return;
        }

        if (order.getOrderItems() != null) {
            order.getOrderItems().forEach((orderItem)->{
                orderItem.setOrder(order);
            });
        }
    }
}

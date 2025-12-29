package com.botmg3002.canteen.schema.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.springframework.stereotype.Component;

import com.botmg3002.canteen.model.OrderItem;
import com.botmg3002.canteen.schema.Item.SubItemTypeMapper;

@Mapper(componentModel = ComponentModel.SPRING, uses = SubItemTypeMapper.class)
@Component
public interface OrderItemMapper {
    
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "itemId", source = "item.id")
    @Mapping(target = "name", source = "item.name")
    @Mapping(target = "price", source = "item.price")
    OrderItemResponse toResponse(OrderItem orderItem);
}

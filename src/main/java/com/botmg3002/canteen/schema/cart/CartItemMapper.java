package com.botmg3002.canteen.schema.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import com.botmg3002.canteen.model.CartItem;
import com.botmg3002.canteen.schema.Item.ItemMapper;
import com.botmg3002.canteen.schema.Item.SubItemTypeMapper;

@Mapper(componentModel = ComponentModel.SPRING, uses = {ItemMapper.class, SubItemTypeMapper.class})
public interface CartItemMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "item.id", source = "itemId")
    @Mapping(target = "subItemType.id", source = "subItemTypeId")
    @Mapping(target = "quantity", source = "quantity", defaultValue = "1")
    CartItem toEntity(CartItemRequest cartItemRequest);

    CartItemResponse toResponse(CartItem cartItem);
}

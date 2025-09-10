package com.botmg3002.canteen.schema.Item;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import com.botmg3002.canteen.model.Item;

@Mapper(
    componentModel = ComponentModel.SPRING,
    uses = SubItemTypeMapper.class
)
public interface ItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "canteen.id", source = "canteenId")
    @Mapping(target = "category.id", source = "categoryId")
    Item toEntity(ItemRequest itemRequest);

    @Mapping(target = "canteenId", source = "canteen.id")
    @Mapping(target = "categoryId", source = "category.id")
    ItemResponse toResponse(Item ItemResponse);
}
package com.botmg3002.canteen.schema.Item;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.springframework.stereotype.Component;

import com.botmg3002.canteen.model.SubItemType;


@Mapper(componentModel = ComponentModel.SPRING)
@Component
public interface SubItemTypeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "item", ignore = true)
    SubItemType toEntity(SubItemTypeRequest subItemTypeRequest);

    @Mapping(target = "itemId", source = "item.id")
    SubItemTypeResponse toResponse(SubItemType subItemType);
}

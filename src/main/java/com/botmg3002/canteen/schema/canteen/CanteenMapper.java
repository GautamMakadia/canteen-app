package com.botmg3002.canteen.schema.canteen;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import com.botmg3002.canteen.model.Canteen;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface CanteenMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Canteen toEntity(CanteenRequest canteenRequest);

    CanteenResponse toResponse(Canteen canteen);
}

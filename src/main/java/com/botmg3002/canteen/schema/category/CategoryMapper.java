package com.botmg3002.canteen.schema.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.botmg3002.canteen.model.Category;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "canteen.id", source = "canteenId")
    Category toEntity(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);
}

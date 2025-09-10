package com.botmg3002.canteen.schema.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.botmg3002.canteen.model.User;


@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequest dto);

    UserDTO toResponse(User entity);
}

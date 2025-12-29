package com.botmg3002.canteen.schema.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import com.botmg3002.canteen.model.User;


@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING
)
@Component
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "admin", ignore = true)
    @Mapping(target = "customer", ignore = true)
    User toEntity(UserRequest dto);

    UserDTO toResponse(User entity);
}

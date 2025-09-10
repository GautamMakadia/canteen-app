package com.botmg3002.canteen.schema.customer;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.botmg3002.canteen.model.Customer;
import com.botmg3002.canteen.schema.user.UserMapper;

@Mapper(componentModel = ComponentModel.SPRING, uses = UserMapper.class)
public interface CustomerMapper {

    CustomerResponse toResponse(Customer customer);
}

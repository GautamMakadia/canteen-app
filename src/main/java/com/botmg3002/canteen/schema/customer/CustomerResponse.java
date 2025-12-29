package com.botmg3002.canteen.schema.customer;

import com.botmg3002.canteen.schema.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private Long id;
    private String name;
    private Long phone;
    private UserDTO user;
}

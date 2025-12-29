package com.botmg3002.canteen.schema.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String email;
    private String password;
    private String role;
    private Long phone;
    private String name;     
}
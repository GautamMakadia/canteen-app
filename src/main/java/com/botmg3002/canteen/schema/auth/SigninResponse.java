package com.botmg3002.canteen.schema.auth;

import com.botmg3002.canteen.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SigninResponse {
    private Long id;
    private String name;
    private Long phone;
    private String email;
    private UserRole role;
}

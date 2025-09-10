package com.botmg3002.canteen.schema.user;

import com.botmg3002.canteen.model.UserRole;


public class UserDTO {

    private Long id;
    private String email;
    private UserRole role;

    public UserDTO(Long id, String email, String password, UserRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}

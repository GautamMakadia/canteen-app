package com.botmg3002.canteen.schema.auth;

import com.botmg3002.canteen.model.UserRole;

public class SigninResponse {
    private Long id;
    private String name;
    private Long phone;
    private String email;
    private UserRole role;

    public SigninResponse() {
    }

    public SigninResponse(Long id, String name, Long phone, String email, UserRole role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    public SigninResponse(Long id, String email, UserRole role) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

}

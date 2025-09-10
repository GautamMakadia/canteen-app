package com.botmg3002.canteen.schema.auth;

public class SignupRequest {

    private String email;
    private String password;
    private String role;
    private Long phone;
    private String name;

    public SignupRequest(String email, String password, String role, Long phone, String name) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

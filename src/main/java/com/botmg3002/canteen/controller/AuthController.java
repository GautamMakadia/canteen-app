package com.botmg3002.canteen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.botmg3002.canteen.schema.auth.LoginRequest;
import com.botmg3002.canteen.schema.auth.LoginResponse;
import com.botmg3002.canteen.schema.auth.SigninResponse;
import com.botmg3002.canteen.schema.auth.SignupRequest;
import com.botmg3002.canteen.security.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<SigninResponse> signup(@RequestBody SignupRequest signupRequest) {
        SigninResponse user = authService.signup(signupRequest);
        return ResponseEntity.ok(user);
    }
}

package com.botmg3002.canteen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.botmg3002.canteen.repository.UserRepository;
import com.botmg3002.canteen.schema.user.UserDTO;
import com.botmg3002.canteen.schema.user.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/user")
public class UserContoller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getActive(@RequestHeader("X-USER-ID") Long id) {

        return userRepository.findById(id)
            .map(user -> ResponseEntity.ok(userMapper.toResponse(user)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    
}

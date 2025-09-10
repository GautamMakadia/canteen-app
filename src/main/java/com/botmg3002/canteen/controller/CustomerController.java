package com.botmg3002.canteen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.botmg3002.canteen.model.User;
import com.botmg3002.canteen.schema.customer.CustomerMapper;
import com.botmg3002.canteen.schema.customer.CustomerResponse;
import com.botmg3002.canteen.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    // get current customer details
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponse> me(Authentication authentication) {
        System.out.println("Authorities: " + authentication.getAuthorities());
        User user = (User) authentication.getPrincipal();
        return customerService.findByUserId(user.getId())
                .map((customer) -> ResponseEntity.ok(customerMapper.toResponse(customer)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}

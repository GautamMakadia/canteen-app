package com.botmg3002.canteen.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botmg3002.canteen.model.Customer;
import com.botmg3002.canteen.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> findByUserId(Long id) {
        return customerRepository.findByUserId(id);
    }
}

package com.botmg3002.canteen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botmg3002.canteen.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserId(Long id);
}

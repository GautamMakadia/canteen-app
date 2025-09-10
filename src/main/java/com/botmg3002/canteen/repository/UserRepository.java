package com.botmg3002.canteen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botmg3002.canteen.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
}

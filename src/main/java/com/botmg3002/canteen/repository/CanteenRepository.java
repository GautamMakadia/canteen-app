package com.botmg3002.canteen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botmg3002.canteen.model.Canteen;

public interface CanteenRepository extends JpaRepository<Canteen, Long> {
    
}

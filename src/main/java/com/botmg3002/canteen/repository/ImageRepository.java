package com.botmg3002.canteen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botmg3002.canteen.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    
}

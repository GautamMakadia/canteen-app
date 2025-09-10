package com.botmg3002.canteen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botmg3002.canteen.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    List<Category> findAllByCanteenId(Long id);
}

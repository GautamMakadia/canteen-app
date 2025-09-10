package com.botmg3002.canteen.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botmg3002.canteen.model.Category;
import com.botmg3002.canteen.repository.CategoryRepository;


@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    public Category save(Category category) {
        return categoryRepository.save(category);
    }


    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Stream<Category> findAllByCanteenId(Long canteenId) {
        return categoryRepository.findAllByCanteenId(canteenId).stream();
    }
}

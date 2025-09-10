package com.botmg3002.canteen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.botmg3002.canteen.model.Category;
import com.botmg3002.canteen.schema.category.CategoryMapper;
import com.botmg3002.canteen.schema.category.CategoryRequest;
import com.botmg3002.canteen.schema.category.CategoryResponse;
import com.botmg3002.canteen.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.save(categoryMapper.toEntity(categoryRequest));
        return ResponseEntity.ok(categoryMapper.toResponse(category));
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return categoryService.findById(id)
                .map((categroy) -> ResponseEntity.ok(categoryMapper.toResponse(categroy)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/canteen/{canteenId}")
    public ResponseEntity<List<CategoryResponse>> findAllByCanteenId(@PathVariable Long canteenId) {
        var categories = categoryService.findAllByCanteenId(canteenId)
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(categories);
    }

}

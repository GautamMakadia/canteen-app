package com.botmg3002.canteen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.botmg3002.canteen.schema.Item.ItemMapper;
import com.botmg3002.canteen.schema.Item.ItemResponse;
import com.botmg3002.canteen.schema.canteen.CanteenMapper;
import com.botmg3002.canteen.schema.canteen.CanteenRequest;
import com.botmg3002.canteen.schema.canteen.CanteenResponse;
import com.botmg3002.canteen.schema.category.CategoryMapper;
import com.botmg3002.canteen.schema.category.CategoryResponse;
import com.botmg3002.canteen.service.CanteenService;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("canteen")
public class CanteenController {

    @Autowired
    private CanteenService canteenService;
    @Autowired
    private CanteenMapper canteenMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ItemMapper itemMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<CanteenResponse> create(@RequestBody CanteenRequest canteenRequest) {
        var canteen = canteenService.save(canteenMapper.toEntity(canteenRequest));

        URI location = URI.create("/canteen/" + canteen.getId());

        return ResponseEntity
                .created(location)
                .body(canteenMapper.toResponse(canteen));
    }

    @GetMapping("{id}")
    public ResponseEntity<CanteenResponse> findById(@PathVariable Long id) {
        return canteenService.findById(id)
                .map((canteen) -> ResponseEntity.ok(canteenMapper.toResponse(canteen)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<Set<ItemResponse>> getMethodName(@PathVariable Long id) {

        return canteenService.findById(id)
                .map((canteen) -> {
                    var response = canteen.getItems()
                            .stream()
                            .map(itemMapper::toResponse)
                            .collect(Collectors.toSet());

                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // categories
    @GetMapping("/{id}/category")
    public ResponseEntity<Set<CategoryResponse>> findCategories(@PathVariable Long id) {
        return canteenService.findById(id)
                .map((canteen) -> {
                    var categories = canteen.getCategories()
                            .stream()
                            .map(categoryMapper::toResponse)
                            .collect(Collectors.toSet());

                    return ResponseEntity.ok(categories);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
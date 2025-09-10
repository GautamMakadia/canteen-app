package com.botmg3002.canteen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.botmg3002.canteen.model.Item;
import com.botmg3002.canteen.schema.Item.ItemMapper;
import com.botmg3002.canteen.schema.Item.ItemRequest;
import com.botmg3002.canteen.schema.Item.ItemResponse;
import com.botmg3002.canteen.service.ItemService;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemMapper itemMapper;

    @PostMapping("")
    public ResponseEntity<ItemResponse> create(@RequestBody ItemRequest itemRequest) {
        Item item = itemService.save(itemMapper.toEntity(itemRequest));

        return ResponseEntity
                .created(URI.create("/item/" + item.getId()))
                .body(itemMapper.toResponse(item));
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemResponse> findById(@PathVariable Long id) {
        return itemService.findById(id)
                .map((item) -> ResponseEntity.ok(itemMapper.toResponse(item)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

}

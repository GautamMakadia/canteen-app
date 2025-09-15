package com.botmg3002.canteen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.botmg3002.canteen.model.Item;
import com.botmg3002.canteen.model.User;
import com.botmg3002.canteen.model.UserRole;
import com.botmg3002.canteen.schema.Item.ItemMapper;
import com.botmg3002.canteen.schema.Item.ItemRequest;
import com.botmg3002.canteen.schema.Item.ItemResponse;
import com.botmg3002.canteen.service.ItemService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<ItemResponse> create(@RequestBody ItemRequest itemRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "only admin can add item");
        }

        Item item = itemService.save(itemMapper.toEntity(itemRequest));

        return ResponseEntity
                .created(URI.create("/item/" + item.getId()))
                .body(itemMapper.toResponse(item));
    }

    @GetMapping("")
    public ResponseEntity<List<ItemResponse>> findAll() {
        return ResponseEntity.ok(
                itemService.findAll()
                        .map(itemMapper::toResponse)
                        .collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemResponse> findById(@PathVariable Long id) {
        return itemService.findById(id)
                .map((item) -> ResponseEntity.ok(itemMapper.toResponse(item)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

}

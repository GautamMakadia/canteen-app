package com.botmg3002.canteen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.botmg3002.canteen.model.CartItem;
import com.botmg3002.canteen.model.Customer;
import com.botmg3002.canteen.schema.cart.CartItemMapper;
import com.botmg3002.canteen.schema.cart.CartItemResponse;
import com.botmg3002.canteen.service.CartItemService;
import com.botmg3002.canteen.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartItemMapper cartItemMapper;

    @PostMapping("/add/item/{itemId}")
    public ResponseEntity<CartItemResponse> addToCart(@RequestHeader("X-USER-ID") Long id,
            @PathVariable Long itemId) {

        Customer customer = customerService
                .findByUserId(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_ACCEPTABLE, "No Customer Found"));

        CartItem cartItem = cartItemService.addCartItem(customer, itemId);

        return ResponseEntity.ok(cartItemMapper.toResponse(cartItem));
    }

    @DeleteMapping("/remove/item/{itemId}")
    public ResponseEntity<String> postMethodName(@RequestHeader("X-USER-ID") Long userId, @PathVariable Long itemId) {
        Customer customer = customerService
                .findByUserId(userId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_ACCEPTABLE, "No Customer Found"));

        if (!cartItemService.removeCartItem(customer, itemId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    public ResponseEntity<List<CartItemResponse>> getMethodName(@RequestHeader("X-USER-ID") Long userId) {
        Customer customer = customerService
                .findByUserId(userId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_ACCEPTABLE, "No Customer Found"));

        return ResponseEntity.ok(
                customer.getCartItems()
                        .stream()
                        .map(cartItemMapper::toResponse)
                        .collect(Collectors.toList())
        );
    }

}

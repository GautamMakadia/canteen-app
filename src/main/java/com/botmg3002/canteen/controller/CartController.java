package com.botmg3002.canteen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import com.botmg3002.canteen.model.CartItem;
import com.botmg3002.canteen.model.Customer;
import com.botmg3002.canteen.model.User;
import com.botmg3002.canteen.schema.cart.CartItemMapper;
import com.botmg3002.canteen.schema.cart.CartItemRemoveResponse;
import com.botmg3002.canteen.schema.cart.CartItemRequest;
import com.botmg3002.canteen.schema.cart.CartItemResponse;
import com.botmg3002.canteen.service.CartItemService;
import com.botmg3002.canteen.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping("/add")
	public ResponseEntity<CartItemResponse> addToCart(Authentication authentication,
			@RequestBody CartItemRequest cartItemRequest) {

		User user = (User) authentication.getPrincipal();

		Customer customer = customerService
				.findByUserId(user.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
						"No Customer Found"));

		CartItem cartItem = cartItemService.addToCart(customer, cartItemRequest);

		return ResponseEntity.ok(cartItemMapper.toResponse(cartItem));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CartItemRemoveResponse> postMethodName(Authentication authentication,
			@PathVariable Long id) {
		User user = (User) authentication.getPrincipal();

		Customer customer = customerService
				.findByUserId(user.getId())
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_ACCEPTABLE,
						"No Customer Found"));

		Integer quantity = cartItemService.removeCartItem(customer, id);

		CartItemRemoveResponse response = new CartItemRemoveResponse(
				quantity == 0 ? "Item Deleted" : "Item Removed", quantity);

		return ResponseEntity.ok(response);
	}

	@GetMapping("")
	public ResponseEntity<List<CartItemResponse>> findByCustomer(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		System.out.println(user);
		Customer customer = customerService
				.findByUserId(user.getId())
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_ACCEPTABLE,
						"No Customer Found"));

		return ResponseEntity.ok(
				customer.getCartItems()
						.stream()
						.map(cartItemMapper::toResponse)
						.collect(Collectors.toList()));
	}

	@GetMapping("/canteen/{canteenId}")
	public ResponseEntity<List<CartItemResponse>> fingByCanteenId(Authentication authentication,
			@PathVariable Long canteenId) {
		User user = (User) authentication.getPrincipal();
		Customer customer = customerService
				.findByUserId(user.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"user is not customer"));

		return ResponseEntity.ok(
				cartItemService.findByCustomerIdAndCanteenId(customer.getId(), canteenId)
						.stream()
						.map(cartItemMapper::toResponse)
						.collect(Collectors.toList()));
	}

}

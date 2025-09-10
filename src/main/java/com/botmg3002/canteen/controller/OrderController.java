package com.botmg3002.canteen.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.botmg3002.canteen.model.Customer;
import com.botmg3002.canteen.model.Order;
import com.botmg3002.canteen.schema.order.OrderMapper;
import com.botmg3002.canteen.schema.order.OrderRequest;
import com.botmg3002.canteen.schema.order.OrderResponse;
import com.botmg3002.canteen.service.CustomerService;
import com.botmg3002.canteen.service.OrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return orderService.findById(id)
                .map((order) -> ResponseEntity.ok(orderMapper.toResponse(order)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.save(orderRequest);

        return ResponseEntity
                .created(URI.create("/order/" + order.getId()))
                .body(orderMapper.toResponse(order));
    }

    @GetMapping("/today")
    public ResponseEntity<List<OrderResponse>> orderResponseEntity() {

        var orders = orderService.findAllByToday()
                .map((order) -> orderMapper.toResponse(order))
                .collect(Collectors.toList());

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderResponse>> getOrderHistry(@RequestHeader("X-USER-ID") Long userId) {
        Optional<Customer> customerOptional = customerService.findByUserId(userId);

        if (customerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Customer customer = customerOptional.get();

        List<OrderResponse> orders = orderService.findByCustomerHistory(customer.getId())
                .stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(orders);
    }

}

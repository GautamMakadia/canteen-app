package com.botmg3002.canteen.controller;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.botmg3002.canteen.event.OrderPublisher;
import com.botmg3002.canteen.model.Customer;
import com.botmg3002.canteen.model.Order;
import com.botmg3002.canteen.model.OrderStatus;
import com.botmg3002.canteen.model.User;
import com.botmg3002.canteen.model.UserRole;
import com.botmg3002.canteen.schema.order.OrderMapper;
import com.botmg3002.canteen.schema.order.OrderRequest;
import com.botmg3002.canteen.schema.order.OrderResponse;
import com.botmg3002.canteen.service.CustomerService;
import com.botmg3002.canteen.service.OrderService;

import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderPublisher orderPublisher;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderMapper orderMapper;

    OrderController(OrderPublisher orderPublisher) {
        this.orderPublisher = orderPublisher;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return orderService.findById(id)
                .map((order) -> ResponseEntity.ok(orderMapper.toResponse(order)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.save(orderRequest);

        return ResponseEntity
                .created(URI.create("/order/" + orderResponse.getId()))
                .body(orderResponse);
    }

    @PutMapping("{orderId}/status")
    public ResponseEntity<OrderResponse> updateStatus(Authentication authentication, @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        User user = (User) authentication.getPrincipal();

        if (user.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Admin Can Change The Order Status.");
        }

        Order order = orderService.updateStatus(orderId, status);

        return ResponseEntity.created(URI.create("/order/" + orderId))
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

    @GetMapping(value = "/stream/status/{status}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<OrderResponse>> createdOrdersStream(Authentication authentication,
            @PathVariable String status) {

        User user = (User) authentication.getPrincipal();

        if (user.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Admin Can See The Order Stream");
        }

        return orderPublisher.getSink().asFlux()
                .filter(order -> order.getStatus().equals(status.toUpperCase()))
                .map(order -> ServerSentEvent.builder(order).build())
                .mergeWith(Flux.interval(Duration.ofSeconds(15)).map((seq) -> ServerSentEvent.<OrderResponse>builder()
                        .comment("ping")
                        .build()));
    }
    
    @GetMapping(value = "/stream/customer", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<OrderResponse>> streamCustomerOrders(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        if (user.getRole() != UserRole.CUSTOMER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Customer Can See Order Stream");
        }
        Customer customer = user.getCustomer();

        return orderPublisher.getSink().asFlux()
                .filter(order -> order.getCustomerId().equals(customer.getId()))
                .map(order -> ServerSentEvent.builder(order).build())
                .mergeWith(Flux.interval(Duration.ofSeconds(15)).map(seq -> ServerSentEvent.<OrderResponse>builder()
                        .comment("ping")
                        .build()));
    }

}

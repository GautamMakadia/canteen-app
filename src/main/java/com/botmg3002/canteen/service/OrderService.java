package com.botmg3002.canteen.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.botmg3002.canteen.event.OrderPublisher;
import com.botmg3002.canteen.model.Canteen;
import com.botmg3002.canteen.model.CartItem;
import com.botmg3002.canteen.model.Customer;
import com.botmg3002.canteen.model.Order;
import com.botmg3002.canteen.model.OrderItem;
import com.botmg3002.canteen.model.OrderStatus;
import com.botmg3002.canteen.repository.*;
import com.botmg3002.canteen.schema.order.OrderMapper;
import com.botmg3002.canteen.schema.order.OrderRequest;
import com.botmg3002.canteen.schema.order.OrderResponse;

import reactor.core.publisher.Sinks.EmitResult;

@Service
@Transactional
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CanteenRepository canteenRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderPublisher orderPublisher;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Stream<Order> findAllByToday() {
        return orderRepository.findAllByToday().stream();
    }

    public OrderResponse save(OrderRequest orderRequest) {
        Order order = new Order();

        Canteen canteen = canteenRepository.findById(orderRequest.getCanteenId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Canteen found."));

        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Customer found."));

        Set<CartItem> cartItems = cartItemRepository.findByCustomerIdAndIdIn(customer.getId(),
                orderRequest.getCartItemIds());

        AtomicInteger total = new AtomicInteger(0);
        Set<OrderItem> orderItems = cartItems
                .stream()
                .map((cartItem) -> {
                    OrderItem orderItem = new OrderItem();

                    var subItemType = cartItem.getSubItemType();
                    final var item = cartItem.getItem();

                    orderItem.setItem(cartItem.getItem());
                    orderItem.setSubItemType(cartItem.getSubItemType());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setOrder(order);

                    int price = item.getPrice();
                    int extraPrice = subItemType == null ? 0 : subItemType.getExtraPrice();
                    int quantitiy = cartItem.getQuantity();

                    total.addAndGet((price + extraPrice) * quantitiy);

                    return orderItem;
                })
                .collect(Collectors.toSet());

        int itemCount = orderItems.stream().mapToInt(OrderItem::getQuantity).sum();

        order.setCanteen(canteen);
        order.setCustomer(customer);
        order.setItemCount(itemCount);
        order.setTotal(Integer.valueOf(total.get()));
        order.setStatus(orderRequest.getStatus());
        order.setOrderItems(orderItems);
        order.setCanteen(canteen);

        Order newOrder = orderRepository.save(order);
        cartItemRepository.deleteByCustomerIdAndItemIdIn(order.getCustomer().getId(), orderRequest.getCartItemIds());

        OrderResponse orderResponse = orderMapper.toResponse(newOrder);

        EmitResult publishResult = orderPublisher.publish(orderResponse);

        if (publishResult.isFailure()) {
            System.err.println("Faild to publish order: " + order.getId());
        }

        return orderResponse;
    }

    public Order updateStatus(Long id, OrderStatus orderStatus) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No order found with id:" + id));

        if (!isValidTransition(order.getStatus(), orderStatus)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Changing Status From:" + order.getStatus() + " to:" + orderStatus + " is not allowed.");
        }

        order.setStatus(orderStatus);

        order = orderRepository.save(order);

        EmitResult publishResult = orderPublisher.publish(orderMapper.toResponse(order));
        
        if (publishResult.isFailure()) {
            System.err.println("Faild to publish order: " + order.getId());
        }

        return order;
    }

    public List<Order> findByCustomerHistory(Long customerId) {
        return orderRepository.findHistoryByCustomer(LocalDateTime.now().minusDays(30), customerId);
    }

    public List<Order> findTodayOrdersByCanteenId(Long canteenId) {
        return orderRepository.findTodayOrdersByCanteenId(canteenId);
    }

    public List<OrderResponse> findTodayOrderByCustomer(Customer customer) {
        return orderRepository.findTodayOrderByCustomer(customer).map(orderMapper::toResponse).toList();
    }

    private boolean isValidTransition(OrderStatus from, OrderStatus to) {
        return switch (from) {
            case CREATED -> (to == OrderStatus.PREPARING || to == OrderStatus.COMPLETED || to == OrderStatus.CANCELED);
            case PREPARING -> (to == OrderStatus.COMPLETED);
            case COMPLETED, CANCELED -> false;
        };
    }
}

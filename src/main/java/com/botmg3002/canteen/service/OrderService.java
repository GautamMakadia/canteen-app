package com.botmg3002.canteen.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.botmg3002.canteen.model.Canteen;
import com.botmg3002.canteen.model.CartItem;
import com.botmg3002.canteen.model.Customer;
import com.botmg3002.canteen.model.Order;
import com.botmg3002.canteen.model.OrderItem;
import com.botmg3002.canteen.repository.*;
import com.botmg3002.canteen.schema.order.OrderRequest;

@Service
public class OrderService {

    private final CustomerRepository customerRepository;

    @Autowired
    private CanteenRepository canteenRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    OrderService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Stream<Order> findAllByToday() {
        return orderRepository.findAllByToday().stream();
    }

    @Transactional
    public Order save(OrderRequest orderRequest) {
        Order order = new Order();

        Canteen canteen = canteenRepository.findById(orderRequest.getCanteenId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Canteen found."));
        

        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Customer found."));
        

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

                    total.addAndGet((price+extraPrice)*quantitiy);

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

        return newOrder;
    }

    public List<Order> findByCustomerHistory(Long customerId) {
        return orderRepository.findHistoryByCustomer(LocalDateTime.now().minusDays(30), customerId);
    }

    public List<Order> findTodayOrdersByCanteenId(Long canteenId) {
        return orderRepository.findTodayOrdersByCanteenId(canteenId);
    }
}

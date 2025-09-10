package com.botmg3002.canteen.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botmg3002.canteen.model.Item;
import com.botmg3002.canteen.model.Order;
import com.botmg3002.canteen.model.OrderItem;
import com.botmg3002.canteen.model.SubItemType;
import com.botmg3002.canteen.repository.CanteenRepository;
import com.botmg3002.canteen.repository.ItemRepository;
import com.botmg3002.canteen.repository.OrderRepository;
import com.botmg3002.canteen.repository.SubItemTypeRepository;
import com.botmg3002.canteen.schema.order.OrderRequest;

@Service
public class OrderService {

    @Autowired
    private CanteenRepository canteenRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SubItemTypeRepository subItemTypeRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Stream<Order> findAllByToday() {
        return orderRepository.findAllByToday().stream();
    }

    public Order save(OrderRequest orderRequest) {
        Order order = new Order();
        
        order.setCanteen(
                canteenRepository.findById(orderRequest.getCanteenId())
                        .orElseThrow(() -> new IllegalArgumentException("null canteen id")));
        System.out.println("Canteen is set");
        order.setItemCount(orderRequest.getItemCount());
        order.setStatus(orderRequest.getStatus());
        order.setTotal(orderRequest.getTotal());
        Set<OrderItem> orderItems = orderRequest.getOrderItems().stream().map((item) -> {
            OrderItem orderItem = new OrderItem();

            if (item.getSubItemTypeId() != null) {
                SubItemType subItemType = subItemTypeRepository.findById(item.getSubItemTypeId())
                        .orElseThrow(() -> new IllegalArgumentException("null subitem id"));

                orderItem.setSubItemType(subItemType);
            }

            Item mItem = itemRepository.findById(item.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("null item id"));

            orderItem.setItem(mItem);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(order);

            return orderItem;
        }).collect(Collectors.toSet());

        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }

    public List<Order> findByCustomerHistory(Long customerId) {
        return orderRepository.findHistoryByCustomer(LocalDateTime.now().minusDays(30), customerId);
    }
}

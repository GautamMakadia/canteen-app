package com.botmg3002.canteen.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botmg3002.canteen.model.CartItem;
import com.botmg3002.canteen.model.Customer;
import com.botmg3002.canteen.model.Item;
import com.botmg3002.canteen.repository.CartItemRepository;
import com.botmg3002.canteen.repository.CustomerRepository;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Optional<CartItem> findByCustomerIdAndItemId(Long customerId, Long itemId) {
        return cartItemRepository.findByCustomerIdAndItemId(customerId, itemId);
    }

    public Set<CartItem> findByCustomerId(Long customerId) {
        var customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isEmpty())
            return Set.of();

        return customerOptional.get().getCartItems();
    }

    public CartItem addCartItem(Customer customer, Long itemId) {

        Optional<CartItem> cartItemOptional = findByCustomerIdAndItemId(customer.getId(), itemId);

        if (cartItemOptional.isPresent()) {
            var mCartItem = cartItemOptional.get();
            mCartItem.incrementQuntity();
            return cartItemRepository.save(cartItemOptional.get());
        }

        CartItem cartItem = new CartItem(customer, new Item(itemId));
        return cartItemRepository.save(cartItem);
    }

    public Boolean removeCartItem(Customer customer, Long itemId) {

        Optional<CartItem> cartItemOptional = findByCustomerIdAndItemId(customer.getId(), itemId);

        if (cartItemOptional.isEmpty()) {
            return false;
        }

        CartItem mCartItem = cartItemOptional.get();

        if (mCartItem.getQuantity() == 1) {
            cartItemRepository.delete(mCartItem);
            return true;
        }

        mCartItem.decrementQuantity();
        cartItemRepository.save(mCartItem);

        return true;
    }

}

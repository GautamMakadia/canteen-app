package com.botmg3002.canteen.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.botmg3002.canteen.model.CartItem;
import com.botmg3002.canteen.model.Customer;
import com.botmg3002.canteen.model.Item;
import com.botmg3002.canteen.model.SubItemType;
import com.botmg3002.canteen.repository.CartItemRepository;
import com.botmg3002.canteen.repository.CustomerRepository;
import com.botmg3002.canteen.repository.ItemRepository;
import com.botmg3002.canteen.repository.SubItemTypeRepository;
import com.botmg3002.canteen.schema.cart.CartItemRequest;

@Service
@Transactional
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SubItemTypeRepository subItemTypeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Set<CartItem> findByCustomerId(Long customerId) {
        var customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isEmpty())
            return Set.of();

        return customerOptional.get().getCartItems();
    }

    public List<CartItem> findByCustomerIdAndCanteenId(Long customerId, Long canteenId) {
        return cartItemRepository.findByCustomerIdAndItemCanteenId(customerId, canteenId);
    }

    @Transactional
    public CartItem addToCart(final Customer customer, final CartItemRequest cartItemRequest) {
        try {
            final boolean isItemExists = itemRepository.existsById(cartItemRequest.getItemId());

            if (!isItemExists) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                        "there is no item with id:" + cartItemRequest.getItemId());
            }

            final Optional<CartItem> existing = cartItemRepository
                    .findCartItem(
                            customer.getId(),
                            cartItemRequest.getItemId(),
                            cartItemRequest.getSubItemTypeId());

            if (existing.isPresent()) {

                CartItem cartItem = existing.get();
                cartItem.incrementQuntity();
                return cartItemRepository.save(cartItem);

            }

            CartItem cartItem = new CartItem();
            Item item = itemRepository.findById(cartItemRequest.getItemId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                            "No Item with " + cartItemRequest.getItemId()));

            cartItem.setCustomer(customer);
            cartItem.setItem(item);

            if (cartItemRequest.getSubItemTypeId() != null) {
                SubItemType subItemType = subItemTypeRepository.findById(cartItemRequest.getSubItemTypeId())
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                                        "No SubItemFound with " + cartItemRequest.getSubItemTypeId()));

                cartItem.setSubItemType(subItemType);
            }
            Integer quantity = cartItemRequest.getQuantity();

            cartItem.setQuantity(quantity == null ? 1 : quantity);

            return cartItemRepository.save(cartItem);

        } catch (OptimisticLockingFailureException ex) {
            System.err.println("Error in addToCart: " + ex.getLocalizedMessage());
            ex.printStackTrace(System.err);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error updating cartItem");
        }
    }

    public Integer removeCartItem(Customer customer, Long id) {
        try {

            CartItem cartItem = cartItemRepository.findByIdAndCustomerId(id, customer.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "No cart item found."));

            if (cartItem.getQuantity() == 1) {
                cartItemRepository.delete(cartItem);
                return 0;
            }

            cartItem.decrementQuantity();

            cartItem = cartItemRepository.save(cartItem);

            return cartItem.getQuantity();
        } catch (OptimisticLockingFailureException ex) {
            System.err.println("Error in removeItemCartItem");
            ex.printStackTrace(System.err);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error updating cartItem");
        }
    }

}

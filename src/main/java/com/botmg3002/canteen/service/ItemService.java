package com.botmg3002.canteen.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botmg3002.canteen.model.Item;
import com.botmg3002.canteen.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }
    public Item save(Item item) {
        
        item.getSubItemTypes().forEach(subItem->subItem.setItem(item));

        return itemRepository.save(item);
    }
    public Stream<Item> findAll() {
        return itemRepository.findAll().stream();
    }
}

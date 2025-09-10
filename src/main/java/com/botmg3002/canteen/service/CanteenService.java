package com.botmg3002.canteen.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botmg3002.canteen.model.Canteen;
import com.botmg3002.canteen.repository.CanteenRepository;

@Service
public class CanteenService {

    @Autowired
    private CanteenRepository canteenRepository;

    public Optional<Canteen> findById(Long id) {
        return canteenRepository.findById(id);
    }

    public Canteen save(Canteen canteen) {
        return canteenRepository.save(canteen);
    }
}

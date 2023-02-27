package com.sm360.backend.service;

import com.sm360.backend.exception.DealerNotFoundException;
import com.sm360.backend.model.Dealer;
import com.sm360.backend.repository.DealerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealerServiceImpl implements DealerService {

    @Autowired
    private DealerRepository dealerRepository;

    @Override
    public Dealer createDealer(String name, int tierLimit) {
        Dealer dealer = new Dealer(name, tierLimit);
        return dealerRepository.save(dealer);
    }

    @Override
    public List<Dealer> getAllDealers() {
        return dealerRepository.findAll();
    }

    @Override
    public Dealer getDealerById(Long id) {
        return dealerRepository.findById(id)
                .orElseThrow(() -> new DealerNotFoundException(id));
    }

    @Override
    public Dealer updateDealer(Long id, String name) {
        Dealer existingDealer = getDealerById(id);
        existingDealer.setName(name);
        return dealerRepository.save(existingDealer);
    }

    @Override
    public void deleteDealer(Long id) {
        Dealer dealer = getDealerById(id);
        dealerRepository.delete(dealer);
    }
}

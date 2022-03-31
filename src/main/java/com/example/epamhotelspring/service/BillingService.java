package com.example.epamhotelspring.service;

import com.example.epamhotelspring.model.Billing;
import com.example.epamhotelspring.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingService {

    private final BillingRepository billingRepository;

    public List<Billing> getUserBillings(Long userId){
        return billingRepository.findBillingsByRoomRequest_UserId(userId);
    }

    @Autowired
    public BillingService(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }
}

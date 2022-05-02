package com.example.epamhotelspring.service.admin;

import com.example.epamhotelspring.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminBillingService {

    private final BillingRepository billingRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int deleteOldBillings(){
        billingRepository.deleteRoomRegistriesConnectedToOldBillings();
        billingRepository.deleteRoomRequestsConnectedToOldBillings();
        return billingRepository.deleteUnpaidOldBillings();
    }

    @Autowired
    public AdminBillingService(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }
}

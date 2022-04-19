package com.example.epamhotelspring.service;

import com.example.epamhotelspring.model.Billing;
import com.example.epamhotelspring.model.RoomRequest;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.model.enums.RequestStatus;
import com.example.epamhotelspring.repository.BillingRepository;
import com.example.epamhotelspring.repository.RoomRequestRepository;
import com.example.epamhotelspring.repository.UserRepository;
import com.example.epamhotelspring.service.utils.ServiceErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BillingService {

    private final BillingRepository billingRepository;

    private final RoomRequestRepository roomRequestRepository;

    private final UserRepository userRepository;

    public Page<Billing> getUserBillings(Long userId, Pageable pageable){
        return billingRepository.findBillingsByRoomRequest_UserId(userId, pageable);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void payBilling(Long billingId, Long userId, ServiceErrors errors){
        Billing billing = billingRepository.findBillingEager(billingId);
        RoomRequest roomRequest = billing.getRoomRequest();
        User dbUser = roomRequest.getUser();
        if(billing.getPaid()){
            return;
        }
        if(!dbUser.getId().equals(userId)){
            return;
        }
        BigDecimal userBalance = dbUser.getBalance();
        if(userBalance.compareTo(billing.getPrice()) < 0){
            errors.reject("errors.notEnoughMoney");
            return;
        }
        dbUser.setBalance(dbUser.getBalance().subtract(billing.getPrice()));
        billing.setPaid(true);
        roomRequest.setStatus(RequestStatus.PAID);
        userRepository.save(dbUser);
        roomRequestRepository.save(roomRequest);
        billingRepository.save(billing);
    }

    @Autowired
    public BillingService(BillingRepository billingRepository, RoomRequestRepository roomRequestRepository, UserRepository userRepository) {
        this.billingRepository = billingRepository;
        this.roomRequestRepository = roomRequestRepository;
        this.userRepository = userRepository;
    }
}

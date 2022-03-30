package com.example.epamhotelspring.service;

import com.example.epamhotelspring.dto.RoomRequestDTO;
import com.example.epamhotelspring.model.Billing;
import com.example.epamhotelspring.model.RoomRequest;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.model.enums.RequestStatus;
import com.example.epamhotelspring.repository.BillingRepository;
import com.example.epamhotelspring.repository.RoomRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RoomRequestService {

    private final RoomRequestRepository roomRequestRepository;

    private final BillingRepository billingRepository;

    public RoomRequest createRoomRequest(RoomRequest roomRequest){
        return roomRequestRepository.save(roomRequest);
    }

    public List<RoomRequestDTO> getUserRoomRequests(Long userId){
        return roomRequestRepository.findRoomRequestsByUserId(userId);
    }

    @Transactional
    public void closeRoomRequest(Long requestId, Long userId){
        //TODO add controller advices to handle exceptions
        RoomRequest roomRequest = roomRequestRepository.findById(requestId).orElseThrow(EntityNotFoundException::new);
        if(!roomRequest.getUser().getId().equals(userId)){
            return;
        }
        roomRequest.setStatus(RequestStatus.CLOSED);
        roomRequestRepository.save(roomRequest);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void acceptRoomRequest(Long requestId, User user){
        RoomRequest roomRequest = roomRequestRepository.findRoomRequestEagerById(requestId).orElseThrow(EntityNotFoundException::new);
        if(!roomRequest.getUser().getId().equals(user.getId())){
            return;
        }
        if(roomRequest.getRoom() == null){
            return;
        }
        roomRequest.setStatus(RequestStatus.AWAITING_PAYMENT);
        Billing billing = new Billing();
        billing.setRoomRequest(roomRequest);
        BigDecimal roomPrice = roomRequest.getRoom().getPrice();
        long stayDaysCount = Duration.between(roomRequest.getCheckInDate().atStartOfDay(), roomRequest.getCheckOutDate().atStartOfDay()).toDays();
        billing.setPrice(roomPrice.multiply(new BigDecimal(stayDaysCount)));
        billing.setPayEndDate(LocalDate.now().plus(2, ChronoUnit.DAYS));
        billingRepository.save(billing);
        roomRequestRepository.save(roomRequest);
    }

    @Autowired
    public RoomRequestService(RoomRequestRepository roomRequestRepository, BillingRepository billingRepository) {
        this.roomRequestRepository = roomRequestRepository;
        this.billingRepository = billingRepository;
    }
}

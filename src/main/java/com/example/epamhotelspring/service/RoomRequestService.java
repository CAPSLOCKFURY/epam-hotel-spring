package com.example.epamhotelspring.service;

import com.example.epamhotelspring.dto.RoomRequestDTO;
import com.example.epamhotelspring.forms.DeclineRoomForm;
import com.example.epamhotelspring.model.Billing;
import com.example.epamhotelspring.model.RoomRegistry;
import com.example.epamhotelspring.model.RoomRequest;
import com.example.epamhotelspring.model.enums.RequestStatus;
import com.example.epamhotelspring.repository.BillingRepository;
import com.example.epamhotelspring.repository.RoomRegistryRepository;
import com.example.epamhotelspring.repository.RoomRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class RoomRequestService {

    private final RoomRequestRepository roomRequestRepository;

    private final BillingRepository billingRepository;

    private final RoomRegistryRepository roomRegistryRepository;

    public RoomRequest createRoomRequest(RoomRequest roomRequest){
        return roomRequestRepository.save(roomRequest);
    }

    public Page<RoomRequestDTO> getUserRoomRequests(Long userId, Pageable pageable){
        return roomRequestRepository.findRoomRequestsByUserId(userId, pageable);
    }

    @Transactional
    public void closeRoomRequest(Long requestId, Long userId){
        //TODO add controller advices to handle exceptions
        RoomRequest roomRequest = roomRequestRepository.findById(requestId).orElseThrow(EntityNotFoundException::new);
        if(!roomRequest.getUser().getId().equals(userId)){
            return;
        }
        if(roomRequest.getStatus() != RequestStatus.AWAITING){
            return;
        }
        roomRequest.setStatus(RequestStatus.CLOSED);
        roomRequestRepository.save(roomRequest);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void acceptRoomRequest(Long requestId, Long userId){
        RoomRequest roomRequest = roomRequestRepository.findRoomRequestEagerById(requestId).orElseThrow(EntityNotFoundException::new);
        if(!roomRequest.getUser().getId().equals(userId)){
            return;
        }
        if(roomRequest.getRoom() == null){
            return;
        }
        roomRequest.setStatus(RequestStatus.AWAITING_PAYMENT);
        Billing billing = new Billing(roomRequest);
        RoomRegistry roomRegistry = new RoomRegistry(roomRequest);
        billingRepository.save(billing);
        roomRequestRepository.save(roomRequest);
        roomRegistryRepository.save(roomRegistry);
    }

    public void declineRoomRequest(Long requestId, Long userId, DeclineRoomForm form){
        RoomRequest roomRequest = roomRequestRepository.findRoomRequestEagerById(requestId).orElseThrow(EntityNotFoundException::new);
        if(roomRequest.getStatus() != RequestStatus.AWAITING_CONFIRMATION) {
            return;
        }
        if(!roomRequest.getUser().getId().equals(userId)){
            return;
        }
        roomRequest.setStatus(RequestStatus.AWAITING);
        roomRequest.setRoom(null);
        roomRequest.setComment(form.getComment());
        roomRequestRepository.save(roomRequest);
    }


    @Autowired
    public RoomRequestService(RoomRequestRepository roomRequestRepository, BillingRepository billingRepository, RoomRegistryRepository roomRegistryRepository) {
        this.roomRequestRepository = roomRequestRepository;
        this.billingRepository = billingRepository;
        this.roomRegistryRepository = roomRegistryRepository;
    }
}

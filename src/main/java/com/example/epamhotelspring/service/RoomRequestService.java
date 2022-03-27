package com.example.epamhotelspring.service;

import com.example.epamhotelspring.dto.RoomRequestDTO;
import com.example.epamhotelspring.model.RoomRequest;
import com.example.epamhotelspring.model.enums.RequestStatus;
import com.example.epamhotelspring.repository.RoomRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RoomRequestService {

    private final RoomRequestRepository repository;

    public RoomRequest createRoomRequest(RoomRequest roomRequest){
        return repository.save(roomRequest);
    }

    public List<RoomRequestDTO> getUserRoomRequests(Long userId, String locale){
        return repository.findRoomRequestsByUserId(userId, locale);
    }

    @Transactional
    public void closeRoomRequest(Long requestId, Long userId){
        //TODO add controller advices to handle exceptions
        RoomRequest roomRequest = repository.findById(requestId).orElseThrow(EntityNotFoundException::new);
        if(!roomRequest.getUser().getId().equals(userId)){
            return;
        }
        roomRequest.setStatus(RequestStatus.CLOSED);
        repository.save(roomRequest);
    }

    @Autowired
    public RoomRequestService(RoomRequestRepository repository) {
        this.repository = repository;
    }
}

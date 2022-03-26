package com.example.epamhotelspring.service;

import com.example.epamhotelspring.dto.RoomRequestDTO;
import com.example.epamhotelspring.model.RoomRequest;
import com.example.epamhotelspring.repository.RoomRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    public RoomRequestService(RoomRequestRepository repository) {
        this.repository = repository;
    }
}

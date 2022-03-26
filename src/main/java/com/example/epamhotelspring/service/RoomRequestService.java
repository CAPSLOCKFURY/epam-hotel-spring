package com.example.epamhotelspring.service;

import com.example.epamhotelspring.model.RoomRequest;
import com.example.epamhotelspring.repository.RoomRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomRequestService {

    private final RoomRequestRepository repository;

    public RoomRequest createRoomRequest(RoomRequest roomRequest){
        return repository.save(roomRequest);
    }

    @Autowired
    public RoomRequestService(RoomRequestRepository repository) {
        this.repository = repository;
    }
}

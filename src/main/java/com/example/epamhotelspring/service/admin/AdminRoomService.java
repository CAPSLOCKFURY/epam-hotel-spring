package com.example.epamhotelspring.service.admin;

import com.example.epamhotelspring.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminRoomService {

    private final RoomRepository roomRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int updateRoomStatuses(){
        return roomRepository.updateRoomStatuses();
    }

    @Autowired
    public AdminRoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
}

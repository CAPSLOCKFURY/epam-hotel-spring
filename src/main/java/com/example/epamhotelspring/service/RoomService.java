package com.example.epamhotelspring.service;

import com.example.epamhotelspring.dto.RoomDetailDTO;
import com.example.epamhotelspring.dto.RoomListDTO;
import com.example.epamhotelspring.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomListDTO> getAllRooms(String locale){
        return roomRepository.findAllRooms(locale);
    }

    public RoomDetailDTO getRoomById(Long id, String locale){
        return roomRepository.findRoomById(id, locale);
    }

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
}

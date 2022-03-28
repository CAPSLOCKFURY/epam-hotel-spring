package com.example.epamhotelspring.service.admin;

import com.example.epamhotelspring.dto.AdminRoomRequestDTO;
import com.example.epamhotelspring.dto.RoomDTO;
import com.example.epamhotelspring.repository.admin.AdminRoomRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminRoomRequestService {

    private final AdminRoomRequestRepository roomRequestRepository;

    public List<AdminRoomRequestDTO> getAdminRoomRequests(){
        return roomRequestRepository.findAdminRoomRequests();
    }

    public AdminRoomRequestDTO getAdminRoomRequestById(Long id){
        return roomRequestRepository.findAdminRoomRequestById(id);
    }

    public List<RoomDTO> findSuitableRoomsForRequest(LocalDate checkInDate, LocalDate checkOutDate) {
        return roomRequestRepository.findSuitableRoomsForRoomRequest(checkInDate, checkOutDate);
    }

    @Autowired
    public AdminRoomRequestService(AdminRoomRequestRepository roomRequestRepository) {
        this.roomRequestRepository = roomRequestRepository;
    }
}

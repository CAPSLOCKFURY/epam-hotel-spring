package com.example.epamhotelspring.service.admin;

import com.example.epamhotelspring.dto.AdminRoomRequestDTO;
import com.example.epamhotelspring.dto.RoomDTO;
import com.example.epamhotelspring.forms.CloseRequestForm;
import com.example.epamhotelspring.model.Room;
import com.example.epamhotelspring.model.RoomRequest;
import com.example.epamhotelspring.model.enums.RequestStatus;
import com.example.epamhotelspring.repository.RoomRepository;
import com.example.epamhotelspring.repository.admin.AdminRoomRequestRepository;
import com.example.epamhotelspring.service.utils.ServiceErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
public class AdminRoomRequestService {

    private final AdminRoomRequestRepository roomRequestRepository;

    private final RoomRepository roomRepository;

    public Page<AdminRoomRequestDTO> getAdminRoomRequests(String roomRequestType, Pageable pageable){
        roomRequestType = roomRequestType.toUpperCase();
        return roomRequestRepository.findAdminRoomRequests(RequestStatus.valueOfOrDefault(roomRequestType), pageable);
    }

    public AdminRoomRequestDTO getAdminRoomRequestById(Long id){
        return roomRequestRepository.findAdminRoomRequestById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Page<RoomDTO> getSuitableRoomsForRequest(LocalDate checkInDate, LocalDate checkOutDate, Pageable pageable) {
        return roomRequestRepository.findSuitableRoomsForRoomRequest(checkInDate, checkOutDate, pageable);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean assignRoomToRequest(Long requestId, Long roomId, ServiceErrors errors){
        RoomRequest roomRequest = roomRequestRepository.findById(requestId).orElseThrow(EntityNotFoundException::new);
        if(roomRequest.getStatus() != RequestStatus.AWAITING) {
            errors.reject("errors.requestStatusIsNotAwaiting");
            return false;
        }
        Long datesOverlapCount = roomRepository.countRoomOverlaps(roomRequest.getCheckInDate(), roomRequest.getCheckOutDate(), roomId);
        if(datesOverlapCount != 0){
            errors.reject("errors.datesOverlap");
            return false;
        }
        boolean roomAssigned = roomRepository.isRoomAssignedToRequest(roomId, roomRequest.getCheckInDate(), roomRequest.getCheckOutDate());
        if(roomAssigned){
            errors.reject("errors.roomAlreadyAssigned");
            return false;
        }
        Room room = roomRepository.findById(roomId).orElseThrow(EntityNotFoundException::new);
        roomRequest.setRoom(room);
        roomRequest.setStatus(RequestStatus.AWAITING_CONFIRMATION);
        roomRequestRepository.save(roomRequest);
        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void closeRoomRequest(CloseRequestForm form, ServiceErrors errors){
        RoomRequest roomRequest = roomRequestRepository.findById(form.getRequestId()).orElseThrow(EntityNotFoundException::new);
        if(roomRequest.getStatus() != RequestStatus.AWAITING_CONFIRMATION && roomRequest.getStatus() != RequestStatus.AWAITING){
            errors.reject("errors.requestStatusIsNotAwaitingOrAwaitingConf");
            return;
        }
        roomRequest.setManagerComment(form.getComment());
        roomRequest.setStatus(RequestStatus.CLOSED);
        roomRequest.setRoom(null);
        roomRequestRepository.save(roomRequest);
    }

    @Autowired
    public AdminRoomRequestService(AdminRoomRequestRepository roomRequestRepository, RoomRepository roomRepository) {
        this.roomRequestRepository = roomRequestRepository;
        this.roomRepository = roomRepository;
    }
}

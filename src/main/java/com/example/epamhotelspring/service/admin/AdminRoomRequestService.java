package com.example.epamhotelspring.service.admin;

import com.example.epamhotelspring.dto.AdminRoomRequestDTO;
import com.example.epamhotelspring.dto.RoomDTO;
import com.example.epamhotelspring.forms.CloseRequestForm;
import com.example.epamhotelspring.model.Room;
import com.example.epamhotelspring.model.RoomRequest;
import com.example.epamhotelspring.model.enums.RequestStatus;
import com.example.epamhotelspring.repository.RoomRepository;
import com.example.epamhotelspring.repository.admin.AdminRoomRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
    public boolean assignRoomToRequest(Long requestId, Long roomId, RedirectAttributes errors){
        RoomRequest roomRequest = roomRequestRepository.findById(requestId).orElseThrow(EntityNotFoundException::new);
        List<String> errorsList = new LinkedList<>();
        if(roomRequest.getStatus() != RequestStatus.AWAITING) {
            errorsList.add("errors.requestStatusIsNotAwaiting");
            errors.addFlashAttribute("errors", errorsList);
            return false;
        }
        Long datesOverlapCount = roomRepository.countRoomOverlaps(roomRequest.getCheckInDate(), roomRequest.getCheckOutDate(), roomId);
        if(datesOverlapCount != 0){
            errorsList.add("errors.datesOverlap");
            errors.addFlashAttribute("errors", errorsList);
            return false;
        }
        boolean roomAssigned = roomRepository.isRoomAssignedToRequest(roomId, roomRequest.getCheckInDate(), roomRequest.getCheckOutDate());
        if(roomAssigned){
            errorsList.add("errors.roomAlreadyAssigned");
            errors.addFlashAttribute("errors", errorsList);
            return false;
        }
        Room room = roomRepository.findById(roomId).orElseThrow(EntityNotFoundException::new);
        roomRequest.setRoom(room);
        roomRequest.setStatus(RequestStatus.AWAITING_CONFIRMATION);
        roomRequestRepository.save(roomRequest);
        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void closeRoomRequest(CloseRequestForm form, RedirectAttributes errors){
        RoomRequest roomRequest = roomRequestRepository.findById(form.getRequestId()).orElseThrow(EntityNotFoundException::new);
        if(roomRequest.getStatus() != RequestStatus.AWAITING_CONFIRMATION && roomRequest.getStatus() != RequestStatus.AWAITING){
            errors.addFlashAttribute("errors", Collections.singletonList("errors.requestStatusIsNotAwaitingOrAwaitingConf"));
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

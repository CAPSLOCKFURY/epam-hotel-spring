package com.example.epamhotelspring.controller.admin;

import com.example.epamhotelspring.dto.AdminRoomRequestDTO;
import com.example.epamhotelspring.dto.RoomDTO;
import com.example.epamhotelspring.forms.CloseRequestForm;
import com.example.epamhotelspring.model.enums.RequestStatus;
import com.example.epamhotelspring.service.admin.AdminRoomRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager")
public class AdminRoomRequestController {

    private final AdminRoomRequestService roomRequestService;

    @GetMapping("/room-requests")
    public String getAdminRoomRequests(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                       @RequestParam(value = "roomRequestType", defaultValue = "awaiting") String roomRequestType){
        Page<AdminRoomRequestDTO> roomRequests = roomRequestService.getAdminRoomRequests(roomRequestType, pageable);
        model.addAttribute("roomRequests", roomRequests);
        return "manager/admin-room-requests";
    }

    @GetMapping("/room-request/{id}")
    public String adminRoomRequestDetails(@PathVariable Long id, Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){
        AdminRoomRequestDTO roomRequest = roomRequestService.getAdminRoomRequestById(id);
        model.addAttribute("roomRequest", roomRequest);
        if(roomRequest.getStatus() == RequestStatus.AWAITING) {
            Page<RoomDTO> suitableRooms = roomRequestService.getSuitableRoomsForRequest(roomRequest.getCheckInDate(), roomRequest.getCheckOutDate(), pageable);
            model.addAttribute("suitableRooms", suitableRooms);
        }
        model.addAttribute("closeRequestForm", new CloseRequestForm());
        return "manager/admin-room-request";
    }

    @PostMapping("/room-request/{id}/assign-room/{roomId}")
    public String assignRoomToRoomRequest(@PathVariable("id") Long requestId, @PathVariable("roomId") Long roomId, RedirectAttributes redirectAttributes, @RequestHeader("Referer") String referer) {
        boolean serviceResultSuccess = roomRequestService.assignRoomToRequest(requestId, roomId, redirectAttributes);
        if(!serviceResultSuccess){
            return "redirect:".concat(referer);
        }
        return "redirect:/manager/room-requests";
    }

    @PostMapping("/room-request/close")
    public String closeRoomRequest(@ModelAttribute("closeRequestForm")CloseRequestForm form, RedirectAttributes redirectAttributes, @RequestHeader("Referer") String referer){
        roomRequestService.closeRoomRequest(form, redirectAttributes);
        return "redirect:".concat(referer);
    }

    @Autowired
    public AdminRoomRequestController(AdminRoomRequestService roomRequestService) {
        this.roomRequestService = roomRequestService;
    }
}

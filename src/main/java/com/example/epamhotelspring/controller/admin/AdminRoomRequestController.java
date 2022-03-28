package com.example.epamhotelspring.controller.admin;

import com.example.epamhotelspring.dto.AdminRoomRequestDTO;
import com.example.epamhotelspring.service.admin.AdminRoomRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/profile/manager")
public class AdminRoomRequestController {

    private final AdminRoomRequestService roomRequestService;

    @GetMapping("/room-requests")
    public String getAdminRoomRequests(Model model){
        List<AdminRoomRequestDTO> roomRequests = roomRequestService.getAdminRoomRequests();
        return null;
    }

    @Autowired
    public AdminRoomRequestController(AdminRoomRequestService roomRequestService) {
        this.roomRequestService = roomRequestService;
    }
}

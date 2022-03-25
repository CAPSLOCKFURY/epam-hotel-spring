package com.example.epamhotelspring.controller;

import com.example.epamhotelspring.dto.RoomDetailDTO;
import com.example.epamhotelspring.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Locale;

@Controller
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/")
    public String mainPage(Model model, Locale locale){
        String localeString = locale.toLanguageTag();
        model.addAttribute("rooms", roomService.getAllRooms(localeString));
        return "index";
    }

    @GetMapping("/room/{id}")
    public String roomDetail(Model model, @PathVariable Long id, Locale locale){
        RoomDetailDTO room = roomService.getRoomById(id, locale.toLanguageTag());
        model.addAttribute("room", room);
        return "room";
    }

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
}

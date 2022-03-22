package com.example.epamhotelspring.controller;

import com.example.epamhotelspring.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
}

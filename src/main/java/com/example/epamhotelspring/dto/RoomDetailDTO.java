package com.example.epamhotelspring.dto;

import com.example.epamhotelspring.model.RoomRegistry;
import com.example.epamhotelspring.model.enums.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RoomDetailDTO {

    private Long id;

    Integer number;

    RoomStatus roomStatus;

    String name;

    BigDecimal price;

    Integer capacity;

    String classTranslated;

    List<RoomRegistry> roomRegistries;

    public RoomDetailDTO(RoomDTO roomDTO, List<RoomRegistry> roomRegistries) {
        id = roomDTO.getId();
        number = roomDTO.getNumber();
        roomStatus = roomDTO.getRoomStatus();
        name = roomDTO.getName();
        price = roomDTO.getPrice();
        capacity = roomDTO.getCapacity();
        classTranslated = roomDTO.getClassTranslated();
        this.roomRegistries = roomRegistries;
    }
}

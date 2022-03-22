package com.example.epamhotelspring.dto;

import com.example.epamhotelspring.model.RoomClass;
import com.example.epamhotelspring.model.enums.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter @AllArgsConstructor
public class RoomListDTO {

    private final Long id;

    private final Integer number;

    private final RoomStatus roomStatus;

    private final String name;

    private final BigDecimal price;

    private final Integer capacity;

    private final String classTranslated;

}

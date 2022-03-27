package com.example.epamhotelspring.dto;

import com.example.epamhotelspring.model.enums.RoomStatus;

import java.math.BigDecimal;

public interface RoomDTO {

    Long getId();

    Integer getNumber();

    RoomStatus getRoomStatus();

    String getName();

    BigDecimal getPrice();

    Integer getCapacity();

    String getClassTranslated();

}
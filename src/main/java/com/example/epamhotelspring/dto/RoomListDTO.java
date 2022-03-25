package com.example.epamhotelspring.dto;

import com.example.epamhotelspring.model.enums.RoomStatus;

import java.math.BigDecimal;

public interface RoomListDTO{

    Long getId();

    Integer getNumber();

    RoomStatus getRoomStatus();

    String getName();

    BigDecimal getPrice();

    Integer getCapacity();

    String getClassTranslated();

}
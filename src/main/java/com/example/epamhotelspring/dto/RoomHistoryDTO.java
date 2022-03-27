package com.example.epamhotelspring.dto;

import com.example.epamhotelspring.model.enums.RoomStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RoomHistoryDTO {

    Long getId();

    Integer getNumber();

    RoomStatus getRoomStatus();

    String getName();

    BigDecimal getPrice();

    Integer getCapacity();

    String getClassTranslated();

    LocalDate getCheckInDate();

    LocalDate getCheckOutDate();
}

package com.example.epamhotelspring.dto;

import com.example.epamhotelspring.model.enums.RequestStatus;

import java.time.LocalDate;

public interface RoomRequestDTO {

    Long getId();

    Long getCapacity();

    String getRoomClass();

    LocalDate getCheckInDate();

    LocalDate getCheckOutDate();

    String getComment();

    RequestStatus getStatus();

    Long getRoomId();

    String getManagerComment();
}

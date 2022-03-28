package com.example.epamhotelspring.dto;

import com.example.epamhotelspring.model.enums.RequestStatus;

import java.time.LocalDate;

public interface AdminRoomRequestDTO {

    Long getId();

    Long getCapacity();

    String getRoomClass();

    LocalDate getCheckInDate();

    LocalDate getCheckOutDate();

    String getComment();

    RequestStatus getStatus();

    Long getRoomId();

    String getManagerComment();

    String getUserFirstName();

    String getUserLastName();

    String getUserEmail();

    String getUserUsername();

}

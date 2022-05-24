package com.example.epamhotelspring.dto;

import com.example.epamhotelspring.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RoomRegistryRefundDTO {

    Long getId();

    User getUser();

    LocalDate getCheckInDate();

    LocalDate getCheckOutDate();

    BigDecimal getRoomPrice();

}

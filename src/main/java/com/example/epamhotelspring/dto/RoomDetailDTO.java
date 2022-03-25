package com.example.epamhotelspring.dto;

import com.example.epamhotelspring.model.enums.RoomStatus;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;

public interface RoomDetailDTO {

    Long getId();

    Integer getNumber();

    RoomStatus getRoomStatus();

    String getName();

    BigDecimal getPrice();

    Integer getCapacity();

    String getClassTranslated();

    List<RoomRegistryDTO> getRoomRegistries();

    interface RoomRegistryDTO{
        java.sql.Date getCheckInDate();

        java.sql.Date getCheckOutDate();
    }

}

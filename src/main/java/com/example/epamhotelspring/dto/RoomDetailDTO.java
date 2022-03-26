package com.example.epamhotelspring.dto;

import com.example.epamhotelspring.model.enums.RoomStatus;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        LocalDate getCheckInDate();

        LocalDate getCheckOutDate();
    }

}

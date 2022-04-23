package com.example.epamhotelspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class RoomRegistryReportDTO {

    private Long id;

    private Long userId;

    private String userFirstName;

    private String userLastName;

    private Long roomId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

}

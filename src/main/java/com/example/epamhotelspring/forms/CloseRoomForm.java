package com.example.epamhotelspring.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class CloseRoomForm {

    private LocalDate startDate;

    private LocalDate endDate;

    private Long roomId;

}

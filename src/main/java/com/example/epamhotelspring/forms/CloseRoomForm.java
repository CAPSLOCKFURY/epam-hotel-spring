package com.example.epamhotelspring.forms;

import com.example.epamhotelspring.validation.constraints.FieldsNotEquals;
import com.example.epamhotelspring.validation.constraints.MinDateToday;
import com.example.epamhotelspring.validation.constraints.OneDateAfterAnother;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@OneDateAfterAnother(beforeDateField = "startDate", afterDateField = "endDate", message = "{errors.checkOutDateIsBeforeCheckIn}")
@FieldsNotEquals(firstField = "startDate", secondField = "endDate", message = "{errors.checkInDateIsCheckOutDate}")
public class CloseRoomForm {

    @MinDateToday
    @NotNull
    private LocalDate startDate;

    @MinDateToday
    @NotNull
    private LocalDate endDate;

    @NotNull
    private Long roomId;

}

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

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@OneDateAfterAnother(beforeDateField = "checkInDate", afterDateField = "checkOutDate", message = "{errors.checkOutDateIsBeforeCheckIn}")
@FieldsNotEquals(firstField = "checkInDate", secondField = "checkOutDate", message = "{errors.checkInDateIsCheckOutDate}")
public class BookRoomForm {

    @NotNull
    Long roomId;

    @NotNull
    @MinDateToday
    private LocalDate checkInDate;

    @NotNull
    @MinDateToday
    private LocalDate checkOutDate;

}

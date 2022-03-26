package com.example.epamhotelspring.forms;

import com.example.epamhotelspring.model.RoomClass;
import com.example.epamhotelspring.validation.constraints.MinDateToday;
import com.example.epamhotelspring.validation.constraints.OneDateAfterAnother;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@OneDateAfterAnother(beforeDateField = "checkInDate", afterDateField = "checkOutDate")
public class RoomRequestForm {

    @Min(1)
    @NotNull
    private Integer capacity;

    @NotNull
    private RoomClass roomClass;

    @NotNull
    @MinDateToday
    private LocalDate checkInDate;

    @NotNull
    @MinDateToday
    private LocalDate checkOutDate;

    private String comment;

    private final static String REPLACE_HTML_REGEX = "<[^>]*>";

    public void setComment(String comment) {
        comment = comment.replaceAll(REPLACE_HTML_REGEX, "");
        this.comment = comment;
    }
}

package com.example.epamhotelspring.utils;

import java.time.Duration;
import java.time.LocalDate;

public class DateUtils {

    public static long diffInDays(LocalDate start, LocalDate end){
        return Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays();
    }

    private DateUtils(){

    }

}

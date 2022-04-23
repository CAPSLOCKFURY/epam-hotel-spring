package com.example.epamhotelspring.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class StringToDateNullableConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        try{
            return LocalDate.parse(source);
        } catch (IllegalArgumentException | DateTimeParseException | NullPointerException e) {
            return null;
        }
    }
}

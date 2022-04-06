package com.example.epamhotelspring.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserUpdateForm {

    @NotEmpty
    @Pattern(regexp = "[а-яА-Я|a-zA-Z]+")
    private String firstName;

    @NotEmpty
    @Pattern(regexp = "[а-яА-Я|a-zA-Z]+")
    private String lastName;

}

package com.example.epamhotelspring.forms;

import com.example.epamhotelspring.validation.constraints.UniqueEmail;
import com.example.epamhotelspring.validation.constraints.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserForm {

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z_0-9-]+")
    @UniqueUsername
    private String username;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z_0-9-]+")
    private String password;

    @NotEmpty
    @Pattern(regexp = "[a-z0-9.]+@[a-z]+(\\.com|\\.net|\\.ukr|\\.ru|\\.ua)")
    @UniqueEmail
    private String email;

    @NotEmpty
    @Pattern(regexp = "[а-яА-Я|a-zA-Z]+")
    private String firstName;

    @NotEmpty
    @Pattern(regexp = "[а-яА-Я|a-zA-Z]+")
    private String lastName;

}

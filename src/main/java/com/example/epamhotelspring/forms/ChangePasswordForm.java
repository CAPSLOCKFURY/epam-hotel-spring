package com.example.epamhotelspring.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ChangePasswordForm {

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z_0-9-]+")
    private String newPassword;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z_0-9-]+")
    private String oldPassword;

}

package com.example.epamhotelspring.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ChangePasswordForm {

    @NotEmpty
    private String newPassword;

    @NotEmpty
    private String oldPassword;

}

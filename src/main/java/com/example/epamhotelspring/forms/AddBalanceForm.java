package com.example.epamhotelspring.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AddBalanceForm {

    @DecimalMin(value = "0.00", inclusive = false, message = "{errors.incorrectMoneyAmount}")
    private BigDecimal amount;

}

package com.example.epamhotelspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain = true)
@NoArgsConstructor @AllArgsConstructor
public class RefundDeleteDTO {

    private Long roomRegistryId;

    private Long billingId;

    private Long roomRequestId;

}

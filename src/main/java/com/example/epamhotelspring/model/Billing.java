package com.example.epamhotelspring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "billings")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private RoomRequest roomRequest;

    @Column(name = "price", precision = 11, scale = 2)
    private BigDecimal price;

    @Column(name = "pay_end_date")
    @ColumnDefault("current_date + interval '2 day'")
    private LocalDate payEndDate;

    @Column(name = "paid")
    private Boolean paid = false;

}

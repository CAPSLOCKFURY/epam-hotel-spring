package com.example.epamhotelspring.model;

import com.example.epamhotelspring.model.enums.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus = RoomStatus.FREE;

    @Column(name = "name")
    private String name;

    @Column(name = "price", precision = 11, scale = 2)
    private BigDecimal price;

    @Column(name = "capacity")
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RoomClass roomClass;

    @Transient
    private String classTranslated;

}

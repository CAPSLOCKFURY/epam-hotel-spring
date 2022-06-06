package com.example.epamhotelspring.model;

import com.example.epamhotelspring.model.enums.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "rooms")
@Accessors(chain = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
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

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<RoomRegistry> roomRegistries;

    @Transient
    private String classTranslated;

    public Room(Integer number, String name, Integer capacity, BigDecimal price, RoomClass roomClass) {
        this.number = number;
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.roomClass = roomClass;
    }
}

package com.example.epamhotelspring.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "room_class_translation")
@Accessors(chain = true) @Getter @Setter
public class RoomClassTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private RoomClass roomClass;

    @Column(name = "name")
    private String name;

    @Column(name = "language")
    private String language;
}

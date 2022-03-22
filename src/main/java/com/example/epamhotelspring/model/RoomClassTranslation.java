package com.example.epamhotelspring.model;

import javax.persistence.*;

@Entity
@Table(name = "room_class_translation")
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

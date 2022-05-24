package com.example.epamhotelspring.model;

import com.example.epamhotelspring.forms.BookRoomForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Accessors(chain = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "room_registry")
public class RoomRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(name = "archived")
    private Boolean archived = false;

    @OneToOne(mappedBy = "roomRegistry", fetch = FetchType.LAZY)
    private Billing billing;

    public RoomRegistry(BookRoomForm form){
        checkInDate = form.getCheckInDate();
        checkOutDate = form.getCheckOutDate();
        this.room = new Room().setId(form.getRoomId());
    }

    public RoomRegistry(RoomRequest roomRequest){
        checkInDate = roomRequest.getCheckInDate();
        checkOutDate = roomRequest.getCheckOutDate();
        user = roomRequest.getUser();
        room = roomRequest.getRoom();
    }

    public RoomRegistry(Room room, LocalDate checkInDate, LocalDate checkOutDate, User user) {
        this.user = user;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
}

package com.example.epamhotelspring.model;

import com.example.epamhotelspring.forms.RoomRequestForm;
import com.example.epamhotelspring.model.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "room_requests")
public class RoomRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @Column(name = "capacity")
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RoomClass roomClass;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.AWAITING;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    private String managerComment;

    @OneToOne(mappedBy = "roomRequest", fetch = FetchType.LAZY)
    private Billing billing;

    public RoomRequest(RoomRequestForm form){
        capacity = form.getCapacity();
        roomClass = form.getRoomClass();
        checkInDate = form.getCheckInDate();
        checkOutDate = form.getCheckOutDate();
        comment = form.getComment();
    }
}

package com.example.epamhotelspring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "billings")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(
            foreignKeyDefinition = "foreign key (roomrequest_id) references room_requests on delete set null"))
    private RoomRequest roomRequest;

    @Column(name = "price", precision = 11, scale = 2)
    private BigDecimal price;

    @Column(name = "pay_end_date")
    @ColumnDefault("current_date + interval '2 day'")
    private LocalDate payEndDate;

    @Column(name = "paid")
    private Boolean paid = false;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(foreignKey = @ForeignKey(
            foreignKeyDefinition = "foreign key (roomregistry_id) references room_registry on delete set null"))
    private RoomRegistry roomRegistry = null;

    public Billing(RoomRequest roomRequest){
        this.roomRequest = roomRequest;
        BigDecimal roomPrice = roomRequest.getRoom().getPrice();
        long stayDaysCount = Duration.between(roomRequest.getCheckInDate().atStartOfDay(), roomRequest.getCheckOutDate().atStartOfDay()).toDays();
        this.price = roomPrice.multiply(new BigDecimal(stayDaysCount));
        this.payEndDate = LocalDate.now().plus(2, ChronoUnit.DAYS);
    }

}

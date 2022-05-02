package com.example.epamhotelspring.repository;

import com.example.epamhotelspring.model.Billing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

    Page<Billing> findBillingsByRoomRequest_UserId(Long userId, Pageable pageable);

    @Query("select b, rr, user from Billing b join b.roomRequest rr join rr.user user where b.id = :billingId")
    Billing findBillingEager(Long billingId);

    @Modifying
    @Query("delete from RoomRequest r where r.id in (select b.roomRequest.id from Billing b where b.payEndDate < current_date and b.paid = false)")
    int deleteRoomRequestsConnectedToOldBillings();

    @Modifying
    @Query("delete from RoomRegistry rr where rr.id in (select b.roomRegistry.id from Billing b where b.payEndDate < current_date and b.paid = false)")
    int deleteRoomRegistriesConnectedToOldBillings();

    @Modifying
    @Query("delete from Billing b where b.payEndDate < current_date and b.paid = false")
    int deleteUnpaidOldBillings();

}

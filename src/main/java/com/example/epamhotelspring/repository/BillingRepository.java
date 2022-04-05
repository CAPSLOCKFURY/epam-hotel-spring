package com.example.epamhotelspring.repository;

import com.example.epamhotelspring.model.Billing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

    Page<Billing> findBillingsByRoomRequest_UserId(Long userId, Pageable pageable);

    @Query("select b, rr, user from Billing b join b.roomRequest rr join rr.user user where b.id = :billingId")
    Billing findBillingEager(Long billingId);

}

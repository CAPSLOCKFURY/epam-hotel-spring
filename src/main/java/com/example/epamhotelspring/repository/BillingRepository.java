package com.example.epamhotelspring.repository;

import com.example.epamhotelspring.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
}

package com.example.epamhotelspring.repository;

import com.example.epamhotelspring.model.RoomRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRequestRepository extends JpaRepository<RoomRequest, Long> {

}

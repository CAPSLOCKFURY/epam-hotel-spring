package com.example.epamhotelspring.repository;

import com.example.epamhotelspring.model.RoomRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRegistryRepository extends JpaRepository<RoomRegistry, Long> {

    List<RoomRegistry> findRoomRegistriesByRoomIdAndArchivedFalse(Long roomId);

}

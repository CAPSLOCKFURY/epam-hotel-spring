package com.example.epamhotelspring.repository;

import com.example.epamhotelspring.dto.BookedDatesDTO;
import com.example.epamhotelspring.model.RoomRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRegistryRepository extends JpaRepository<RoomRegistry, Long> {

    List<BookedDatesDTO> findRoomRegistriesByRoomIdAndArchivedFalse(Long roomId);

    @Modifying
    @Query("update RoomRegistry rr set rr.archived = true where rr.checkOutDate < current_date and rr.archived = false ")
    int archiveOldRoomRegistries();

    @Modifying
    @Query("delete from RoomRegistry rr where rr.id in :ids")
    void deleteByIdIn(Iterable<Long> ids);

}

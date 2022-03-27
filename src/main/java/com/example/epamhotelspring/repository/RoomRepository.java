package com.example.epamhotelspring.repository;

import com.example.epamhotelspring.dto.RoomDetailDTO;
import com.example.epamhotelspring.dto.RoomDTO;
import com.example.epamhotelspring.model.Room;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r.id as id, r.number as number, r.roomStatus as roomStatus, r.name as name, " +
            "r.price as price, r.capacity as capacity, rct.name as classTranslated " +
            "from Room r left join r.roomClass rc " +
            "left join rc.roomClassTranslations rct on rct.language = :locale")
    List<RoomDTO> findAllRooms(String locale, Sort sort);

    @Query("select r.id as id, r.number as number, r.roomStatus as roomStatus, r.name as name, " +
            "r.price as price, r.capacity as capacity, rct.name as classTranslated " +
            "from Room r left join r.roomClass rc " +
            "left join rc.roomClassTranslations rct on rct.language = :locale " +
            "where r.id = :id")
    RoomDTO findRoomById(Long id, String locale);

    @Query("select count(rr.id) from RoomRegistry rr where rr.archived = false and rr.room.id = :roomId " +
            "and (:checkInDate <= rr.checkOutDate and :checkOutDate >= rr.checkInDate)")
    Long countRoomOverlaps(LocalDate checkInDate, LocalDate checkOutDate, Long roomId);

}

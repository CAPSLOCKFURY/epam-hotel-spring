package com.example.epamhotelspring.repository;

import com.example.epamhotelspring.dto.RoomListDTO;
import com.example.epamhotelspring.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select new com.example.epamhotelspring.dto.RoomListDTO(r.id, r.number, r.roomStatus, r.name, r.price, r.capacity, rct.name)" +
            "from Room r left join r.roomClass rc " +
            "left join rc.roomClassTranslations rct on rct.language = :locale")
    List<RoomListDTO> findAllRooms(String locale);

}

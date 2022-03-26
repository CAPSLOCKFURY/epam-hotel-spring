package com.example.epamhotelspring.repository;

import com.example.epamhotelspring.dto.RoomDetailDTO;
import com.example.epamhotelspring.dto.RoomListDTO;
import com.example.epamhotelspring.model.Room;
import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r.id as id, r.number as number, r.roomStatus as roomStatus, r.name as name, " +
            "r.price as price, r.capacity as capacity, rct.name as classTranslated " +
            "from Room r left join r.roomClass rc " +
            "left join rc.roomClassTranslations rct on rct.language = :locale")
    List<RoomListDTO> findAllRooms(String locale, Sort sort);

    @Query("select r.id as id, r.number as number, r.roomStatus as roomStatus, r.name as name, " +
            "r.price as price, r.capacity as capacity, rct.name as classTranslated, " +
            "rr as roomRegistries " +
            "from Room r left join r.roomClass rc " +
            "left join rc.roomClassTranslations rct on rct.language = :locale " +
            "left join r.roomRegistries rr on rr.archived = false " +
            "where r.id = :id")
    RoomDetailDTO findRoomById(@Param("id") Long id, @Param("locale") String locale);

}

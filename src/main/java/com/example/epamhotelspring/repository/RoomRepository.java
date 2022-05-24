package com.example.epamhotelspring.repository;

import com.example.epamhotelspring.dto.RoomDTO;
import com.example.epamhotelspring.dto.RoomHistoryDTO;
import com.example.epamhotelspring.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r.id as id, r.number as number, r.roomStatus as roomStatus, r.name as name, " +
            "r.price as price, r.capacity as capacity, rct.name as classTranslated " +
            "from Room r left join r.roomClass rc " +
            "left join rc.roomClassTranslations rct on rct.language = ?#{T(org.springframework.context.i18n.LocaleContextHolder).getLocale().toLanguageTag()} ")
    Page<RoomDTO> findAllRooms(Pageable pageable);

    @Query("select r.id as id, r.number as number, r.roomStatus as roomStatus, r.name as name, " +
            "r.price as price, r.capacity as capacity, rct.name as classTranslated " +
            "from Room r left join r.roomClass rc " +
            "left join rc.roomClassTranslations rct on rct.language = ?#{T(org.springframework.context.i18n.LocaleContextHolder).getLocale().toLanguageTag()} " +
            "where r.id = :id")
    RoomDTO findRoomById(Long id);

    @Query("select count(rr.id) from RoomRegistry rr where rr.archived = false and rr.room.id = :roomId " +
            "and (:checkInDate <= rr.checkOutDate and :checkOutDate >= rr.checkInDate)")
    Long countRoomOverlaps(LocalDate checkInDate, LocalDate checkOutDate, Long roomId);

    @Query("select r.id as id, r.number as number, r.roomStatus as roomStatus, r.name as name, " +
            "r.price as price, r.capacity as capacity, rct.name as classTranslated, rr.checkInDate as checkInDate, rr.checkOutDate as checkOutDate " +
            "from Room r left join r.roomClass rc " +
            "left join rc.roomClassTranslations rct on rct.language = ?#{T(org.springframework.context.i18n.LocaleContextHolder).getLocale().toLanguageTag()} " +
            "join r.roomRegistries rr " +
            "where rr.user.id = :userId ")
    Page<RoomHistoryDTO> findUserRoomHistory(Long userId, Pageable pageable);

    @Query("select count(rr.id) > 0 from RoomRequest rr where rr.room.id = :roomId and rr.status = 'AWAITING_CONFIRMATION' " +
            "and (:checkInDate <= rr.checkOutDate and :checkOutDate >= rr.checkInDate) ")
    boolean isRoomAssignedToRequest(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);

    @Modifying
    @Query("update RoomRequest rr set rr.room = null, rr.status = 'AWAITING' where rr.status = 'AWAITING_CONFIRMATION' and (:checkInDate <= rr.checkOutDate and :checkOutDate >= rr.checkInDate)")
    void unAssignRoomOnOverlappingDates(LocalDate checkInDate, LocalDate checkOutDate);

    //TODO add this to AdminRoomRepository
    @Modifying
    @Query("update Room r set r.roomStatus = " +
            "case " +
            "   when r.id in (select rr.room.id from RoomRegistry rr where rr.checkOutDate = current_date and rr.archived = false) then ?#{T(com.example.epamhotelspring.model.enums.RoomStatus).FREE} " +
            "   when r.id in (select rr.room.id from RoomRegistry  rr where rr.checkInDate = current_date and rr.archived = false ) then ?#{T(com.example.epamhotelspring.model.enums.RoomStatus).OCCUPIED} " +
            "   else ?#{T(com.example.epamhotelspring.model.enums.RoomStatus).FREE} " +
            "end " +
            "where r.roomStatus <> ?#{T(com.example.epamhotelspring.model.enums.RoomStatus).UNAVAILABLE}")
    int updateRoomStatuses();
}

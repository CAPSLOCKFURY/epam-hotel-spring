package com.example.epamhotelspring.repository.admin;

import com.example.epamhotelspring.dto.AdminRoomRequestDTO;
import com.example.epamhotelspring.dto.RoomDTO;
import com.example.epamhotelspring.model.RoomRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdminRoomRequestRepository extends JpaRepository<RoomRequest, Long> {

    @Query("select r.id as id, r.capacity as capacity, rct.name as roomClass, r.checkInDate as checkInDate, " +
            "r.checkOutDate as checkOutDate, r.comment as comment, r.status as status, r.room.id as roomId, r.managerComment as managerComment, " +
            "user.firstName as userFirstName, user.lastName as userLastName, user.email as userEmail, user.username as userUsername " +
            "from RoomRequest r " +
            "left join r.roomClass rc " +
            "left join rc.roomClassTranslations rct on rct.language = ?#{T(org.springframework.context.i18n.LocaleContextHolder).getLocale().toLanguageTag()} " +
            "left join r.user user"
    )
    List<AdminRoomRequestDTO> findAdminRoomRequests();

    @Query("select r.id as id, r.capacity as capacity, rct.name as roomClass, r.checkInDate as checkInDate, " +
            "r.checkOutDate as checkOutDate, r.comment as comment, r.status as status, r.room.id as roomId, r.managerComment as managerComment, " +
            "user.firstName as userFirstName, user.lastName as userLastName, user.email as userEmail, user.username as userUsername " +
            "from RoomRequest r " +
            "left join r.roomClass rc " +
            "left join rc.roomClassTranslations rct on rct.language = ?#{T(org.springframework.context.i18n.LocaleContextHolder).getLocale().toLanguageTag()} " +
            "left join r.user user " +
            "where r.id = :requestId "
    )
    AdminRoomRequestDTO findAdminRoomRequestById(Long requestId);

    @Query("select r.id as id, r.number as number, r.roomStatus as roomStatus, r.name as name, " +
            "r.price as price, r.capacity as capacity, rct.name as classTranslated " +
            "from Room r left join r.roomClass rc " +
            "left join rc.roomClassTranslations rct on rct.language = ?#{T(org.springframework.context.i18n.LocaleContextHolder).getLocale().toLanguageTag()} " +
            "where r.roomStatus <> 'UNAVAILABLE' and " +
            "r.id not in " +
            "(select distinct rr.room.id from RoomRegistry rr where (:checkInDate <= rr.checkOutDate and :checkOutDate >= rr.checkInDate) " +
            " and rr.archived = false" +
            ") and r.id not in (select distinct rq.room.id from RoomRequest rq where rq.room.id is not null and (:checkInDate <= rq.checkOutDate and :checkOutDate >= rq.checkInDate)) "
    )
    List<RoomDTO> findSuitableRoomsForRoomRequest(LocalDate checkInDate, LocalDate checkOutDate);

}

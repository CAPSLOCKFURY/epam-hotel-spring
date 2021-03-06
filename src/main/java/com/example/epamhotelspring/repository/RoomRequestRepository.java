package com.example.epamhotelspring.repository;

import com.example.epamhotelspring.dto.RoomRequestDTO;
import com.example.epamhotelspring.model.RoomRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRequestRepository extends JpaRepository<RoomRequest, Long> {

    @Query("select r.id as id, r.capacity as capacity, rct.name as roomClass, r.checkInDate as checkInDate, " +
            "r.checkOutDate as checkOutDate, r.comment as comment, r.status as status, r.room.id as roomId, r.managerComment as managerComment " +
            "from RoomRequest r " +
            "left join r.roomClass rc " +
            "left join rc.roomClassTranslations rct on rct.language = ?#{T(org.springframework.context.i18n.LocaleContextHolder).getLocale().toLanguageTag()} " +
            "where r.user.id = :userId")
    Page<RoomRequestDTO> findRoomRequestsByUserId(Long userId, Pageable pageable);

    @Query("select rr, room, user from RoomRequest rr join rr.room room join rr.user user where rr.id = :requestId")
    Optional<RoomRequest> findRoomRequestEagerById(Long requestId);

    @Modifying
    @Query("delete from RoomRequest rr where rr.id in :ids")
    void deleteByIdIn(Iterable<Long> ids);
}

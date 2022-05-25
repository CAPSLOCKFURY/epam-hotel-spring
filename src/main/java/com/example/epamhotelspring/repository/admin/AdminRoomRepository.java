package com.example.epamhotelspring.repository.admin;

import com.example.epamhotelspring.dto.RefundDeleteDTO;
import com.example.epamhotelspring.dto.RoomRegistryRefundDTO;
import com.example.epamhotelspring.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdminRoomRepository extends JpaRepository<Room, Long> {

    @Query("select rr.id as id, rr.user as user, rr.checkInDate as checkInDate, rr.checkOutDate as checkOutDate, room.price as roomPrice " +
            "from RoomRegistry rr left join rr.billing b join rr.room room where rr.room.id = :roomId and (:startDate <= rr.checkOutDate and :endDate >= rr.checkInDate) " +
            "and (b.id is null or b.paid = true)")
    List<RoomRegistryRefundDTO> findRoomRegistriesForRefund(Long roomId, LocalDate startDate, LocalDate endDate);

    @Query("select new com.example.epamhotelspring.dto.RefundDeleteDTO(rr.id, b.id, req.id) " +
            "from RoomRegistry rr left join rr.billing b left join b.roomRequest req left join req.room room where rr.room.id = :roomId and (:startDate <= rr.checkOutDate and :endDate >= rr.checkInDate) " +
            "and (b.id is null or b.paid = true)")
    List<RefundDeleteDTO> findIdForDeletionAfterRefund(Long roomId, LocalDate startDate, LocalDate endDate);

}

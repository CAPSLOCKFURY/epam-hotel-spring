package com.example.epamhotelspring.repository.admin;

import com.example.epamhotelspring.dto.RoomRegistryReportDTO;
import com.example.epamhotelspring.model.RoomRegistry;
import com.example.epamhotelspring.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class AdminRoomRegistryRepository {

    @PersistenceContext
    EntityManager em;

    public List<RoomRegistryReportDTO> findRoomRegistriesForPdfReport(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RoomRegistryReportDTO> cq = cb.createQuery(RoomRegistryReportDTO.class);
        Root<RoomRegistry> roomRegistry = cq.from(RoomRegistry.class);
        Join<RoomRegistry, User> userJoin = roomRegistry.join("user");
        CompoundSelection<RoomRegistryReportDTO> dto = cb.construct(RoomRegistryReportDTO.class,
                roomRegistry.get("id"),
                userJoin.get("id"),
                userJoin.get("firstName"),
                userJoin.get("lastName"),
                roomRegistry.get("room").get("id"),
                roomRegistry.get("checkInDate"),
                roomRegistry.get("checkOutDate")
        );
        cq.select(dto);
        TypedQuery<RoomRegistryReportDTO> query = em.createQuery(cq);
        List<RoomRegistryReportDTO> result = query.getResultList();
        return result;
    }

}

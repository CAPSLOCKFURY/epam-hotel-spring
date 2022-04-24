package com.example.epamhotelspring.repository.admin;

import com.example.epamhotelspring.dto.RoomRegistryReportDTO;
import com.example.epamhotelspring.model.RoomRegistry;
import com.example.epamhotelspring.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Repository
public class AdminRoomRegistryRepository {

    @PersistenceContext
    EntityManager em;

    public List<RoomRegistryReportDTO> findRoomRegistriesForPdfReport(LocalDate checkInDate, LocalDate checkOutDate, Pageable pageable){
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
        List<Predicate> predicates = getRoomRegistryFilterPredicates(cb, roomRegistry, checkInDate, checkOutDate);
        cq.select(dto);
        if(!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }
        TypedQuery<RoomRegistryReportDTO> query = em.createQuery(cq).setFirstResult((int)pageable.getOffset()).setMaxResults(pageable.getPageSize());
        List<RoomRegistryReportDTO> result = query.getResultList();
        return result;
    }

    private List<Predicate> getRoomRegistryFilterPredicates(CriteriaBuilder cb, Path<RoomRegistry> root, LocalDate checkInDate, LocalDate checkOutDate){
        List<Predicate> predicates = new LinkedList<>();
        if(checkInDate != null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("checkInDate"), checkInDate));
        }
        if(checkOutDate != null){
            predicates.add(cb.lessThanOrEqualTo(root.get("checkOutDate"), checkOutDate));
        }
        return predicates;
    }

}

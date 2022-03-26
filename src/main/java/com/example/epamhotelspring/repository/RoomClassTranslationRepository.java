package com.example.epamhotelspring.repository;

import com.example.epamhotelspring.dto.RoomClassTranslationDTO;
import com.example.epamhotelspring.model.RoomClassTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomClassTranslationRepository extends JpaRepository<RoomClassTranslation, Long> {

    List<RoomClassTranslationDTO> findRoomClassTranslationsByLanguage(String language);

}

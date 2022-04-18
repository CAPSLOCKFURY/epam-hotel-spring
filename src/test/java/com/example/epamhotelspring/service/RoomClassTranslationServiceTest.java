package com.example.epamhotelspring.service;

import com.example.epamhotelspring.dto.RoomClassTranslationDTO;
import com.example.epamhotelspring.model.RoomClass;
import com.example.epamhotelspring.model.RoomClassTranslation;
import com.example.epamhotelspring.repository.RoomClassRepository;
import com.example.epamhotelspring.repository.RoomClassTranslationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@TestPropertySource(locations = "/application-test.properties")
public class RoomClassTranslationServiceTest {

    @Autowired
    private RoomClassTranslationService roomClassTranslationService;

    @Autowired
    private RoomClassTranslationRepository roomClassTranslationRepository;

    @Autowired
    private RoomClassRepository roomClassRepository;

    @Test
    void getRoomClassesByLanguageTest(){
        RoomClass roomClass = new RoomClass();
        roomClass = roomClassRepository.save(roomClass);
        RoomClassTranslation ruRoomClassTranslation = new RoomClassTranslation(roomClass, "ru", "ruName");
        roomClassTranslationRepository.save(ruRoomClassTranslation);
        RoomClassTranslation enRoomClassTranslation = new RoomClassTranslation(roomClass, "en", "enName");
        roomClassTranslationRepository.save(enRoomClassTranslation);
        List<RoomClassTranslationDTO> roomClassTranslations = roomClassTranslationService.getRoomClassesByLanguage("en");

        List<String> roomClassTranslationNames = roomClassTranslations.stream().map(RoomClassTranslationDTO::getName).collect(Collectors.toList());
        assertTrue(roomClassTranslationNames.contains("enName") && !roomClassTranslationNames.contains("ruName"));
    }


}

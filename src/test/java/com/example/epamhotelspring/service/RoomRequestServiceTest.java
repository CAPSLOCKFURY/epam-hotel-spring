package com.example.epamhotelspring.service;

import com.example.epamhotelspring.fixtures.RoomDataGenerator;
import com.example.epamhotelspring.forms.RoomRequestForm;
import com.example.epamhotelspring.model.*;
import com.example.epamhotelspring.repository.*;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@TestPropertySource(locations = "/application-test.properties")
public class RoomRequestServiceTest {

    @Autowired
    private RoomRequestService roomRequestService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomClassRepository roomClassRepository;

    @Autowired
    private RoomClassTranslationRepository rctRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRequestRepository roomRequestRepository;

    private static User user;

    private static Room room;

    private static RoomClass roomClass;

    @BeforeAll
    public void setUp(){
        User user = new User().setUsername("roomsTester").setPassword("password").setEmail("roomTester@gmail.com")
                .setFirstName("Room").setLastName("Roomich").setBalance(new BigDecimal(100));
        RoomRequestServiceTest.user = userRepository.save(user);
        RoomClass roomClass = new RoomClass();
        RoomClassTranslation rct = new RoomClassTranslation().setLanguage("en").setName("someClassName").setRoomClass(roomClass);
        RoomRequestServiceTest.roomClass = roomClassRepository.save(roomClass);
        rctRepository.save(rct);
        Room room = new Room().setNumber(100).setName(String.valueOf(100)).setCapacity(100)
                .setRoomClass(roomClass).setPrice(new BigDecimal(100 * 2));
        RoomRequestServiceTest.room = roomRepository.save(room);
    }

    @Test
    void createRoomRequestTest(){
        LocalDate today = LocalDate.now();
        RoomRequestForm form = new RoomRequestForm(1, roomClass, today, today.plus(7, ChronoUnit.DAYS), "");
        RoomRequest roomRequest = new RoomRequest(form);
        roomRequest.setUser(user);
        roomRequestService.createRoomRequest(roomRequest);
        assertEquals(1, roomRequestRepository.count());
    }

}

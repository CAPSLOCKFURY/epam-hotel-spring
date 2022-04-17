package com.example.epamhotelspring.service;

import com.example.epamhotelspring.dto.RoomDTO;
import com.example.epamhotelspring.dto.RoomDetailDTO;
import com.example.epamhotelspring.dto.RoomHistoryDTO;
import com.example.epamhotelspring.fixtures.RoomDataGenerator;
import com.example.epamhotelspring.forms.BookRoomForm;
import com.example.epamhotelspring.model.*;
import com.example.epamhotelspring.repository.*;
import com.example.epamhotelspring.service.utils.ServiceErrors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@TestPropertySource(locations = "/application-test.properties")
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomClassRepository roomClassRepository;

    @Autowired
    private RoomClassTranslationRepository rctRepository;

    @Autowired
    private RoomRegistryRepository roomRegistryRepository;

    @Autowired
    private UserRepository userRepository;

    private final static int roomCount = 5;

    private static User user;

    private static Room room;

    @BeforeAll
    public void setUp(){
        User user = new User().setUsername("roomsTester").setPassword("password").setEmail("roomTester@gmail.com")
                .setFirstName("Room").setLastName("Roomich").setBalance(new BigDecimal(roomCount * 1000));
        RoomServiceTest.user = userRepository.save(user);
        RoomClass roomClass = new RoomClass();
        RoomClassTranslation rct = new RoomClassTranslation().setLanguage("en").setName("cheap");
        roomClassRepository.save(roomClass);
        rct.setRoomClass(roomClass);
        rctRepository.save(rct);
        Iterable<? extends Room> rooms = RoomDataGenerator.generateRooms(roomCount, roomClass);
        List<? extends Room> dbRooms = roomRepository.saveAll(rooms);
        RoomServiceTest.room = dbRooms.get(0);
    }

    @Test
    void getAllRoomsTest(){
        Pageable pageable = Pageable.ofSize(roomCount);
        Page<RoomDTO> rooms = roomService.getAllRooms(pageable);
        assertEquals(roomCount, rooms.getNumberOfElements());
    }

    @Test
    void getRoomByIdTest(){
        Long roomId = RoomServiceTest.room.getId();
        RoomDetailDTO room = roomService.getRoomById(roomId);
        assertNotNull(room);
        assertEquals(roomId, room.getId());
    }

    @Test
    void bookRoomTest(){
        Long roomId = RoomServiceTest.room.getId();
        BookRoomForm form = new BookRoomForm(roomId, LocalDate.now().atStartOfDay().toLocalDate(), LocalDate.now().atStartOfDay().plus(7, ChronoUnit.DAYS).toLocalDate());
        ServiceErrors serviceErrors = new ServiceErrors();
        roomService.bookRoom(form, user, serviceErrors);
        assertTrue(serviceErrors.getErrors().isEmpty());
        assertEquals(1, roomRegistryRepository.count());
    }

    @Test
    void getUserRoomHistoryTest(){
        LocalDate today = LocalDate.now();
        RoomRegistry roomRegistry = new RoomRegistry().setRoom(room).setCheckInDate(today)
                .setCheckOutDate(today.plus(7, ChronoUnit.DAYS))
                .setUser(user);
        roomRegistryRepository.save(roomRegistry);
        RoomRegistry secondRoomRegistry = new RoomRegistry().setRoom(room).setCheckInDate(today.minus(14, ChronoUnit.DAYS))
                .setCheckOutDate(today.minus(7, ChronoUnit.DAYS))
                .setUser(user).setArchived(true);
        roomRegistryRepository.save(secondRoomRegistry);

        Pageable pageable = Pageable.ofSize(10);
        Page<RoomHistoryDTO> roomRegistries = roomService.getUserRoomHistory(user.getId(), pageable);
        assertNotNull(roomRegistries);
        assertEquals(2, roomRegistries.getNumberOfElements());
    }


}

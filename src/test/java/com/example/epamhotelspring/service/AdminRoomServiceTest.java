package com.example.epamhotelspring.service;

import com.example.epamhotelspring.forms.CloseRoomForm;
import com.example.epamhotelspring.model.*;
import com.example.epamhotelspring.model.enums.RequestStatus;
import com.example.epamhotelspring.model.enums.RoomStatus;
import com.example.epamhotelspring.repository.*;
import com.example.epamhotelspring.service.admin.AdminRoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
@TestPropertySource(locations = "/application-test.properties")
public class AdminRoomServiceTest {

    @Autowired
    private AdminRoomService adminRoomService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private RoomRegistryRepository roomRegistryRepository;

    @Autowired
    private RoomRequestRepository roomRequestRepository;

    @Autowired
    private RoomClassRepository roomClassRepository;

    @Test
    void roomCloseTest(){
        User user1 = new User("user1", "pass", "email1@gmail.com", "User1", "User1");
        User user2 = new User("user2", "pass", "email2@gmail.com", "User2", "User2").setBalance(new BigDecimal(100));
        RoomClass roomClass = new RoomClass();
        roomClassRepository.save(roomClass);
        Room room = new Room(1, "1", 4, new BigDecimal(100), roomClass);
        userRepository.save(user1);
        userRepository.save(user2);
        roomRepository.save(room);
        LocalDate today = LocalDate.now();
        LocalDate weekAway = today.plus(7, ChronoUnit.DAYS);
        RoomRequest user2RoomRequest = new RoomRequest(user2, roomClass, 2, today, weekAway).setStatus(RequestStatus.PAID).setRoom(room);
        roomRequestRepository.save(user2RoomRequest);
        Billing user2Billing = new Billing(user2RoomRequest).setPaid(true);
        billingRepository.save(user2Billing);
        RoomRegistry user2RoomRegistry = new RoomRegistry(room, today, weekAway, user2);
        roomRegistryRepository.save(user2RoomRegistry);
        user2Billing.setRoomRegistry(user2RoomRegistry);
        billingRepository.save(user2Billing);
        RoomRegistry user1RoomRegistry = new RoomRegistry(room, weekAway, weekAway.plus(14, ChronoUnit.DAYS), user1);
        roomRegistryRepository.save(user1RoomRegistry);

        adminRoomService.closeRoom(new CloseRoomForm(today, weekAway.plus(27, ChronoUnit.DAYS), room.getId()));
        User dbUser1 = userRepository.getById(user1.getId());
        User dbUser2 = userRepository.getById(user2.getId());
        Room dbRoom = roomRepository.getById(room.getId());
        assertEquals(new BigDecimal("1400.00"), dbUser1.getBalance());
        assertEquals(new BigDecimal("800.00"), dbUser2.getBalance());
        assertEquals(dbRoom.getRoomStatus(), RoomStatus.UNAVAILABLE);

        assertFalse(roomRegistryRepository.existsById(user2RoomRegistry.getId()));
        assertFalse(roomRegistryRepository.existsById(user1RoomRegistry.getId()));
        assertFalse(roomRequestRepository.existsById(user2RoomRequest.getId()));
        assertFalse(billingRepository.existsById(user2Billing.getId()));
    }

    @Test
    void closeRoomSummingRefundTest(){
        User user1 = new User("user1", "pass", "email1@gmail.com", "User1", "User1");
        User user2 = new User("user2", "pass", "email2@gmail.com", "User2", "User2").setBalance(new BigDecimal(100));
        RoomClass roomClass = new RoomClass();
        roomClassRepository.save(roomClass);
        Room room = new Room(1, "1", 4, new BigDecimal(100), roomClass);
        userRepository.save(user1);
        userRepository.save(user2);
        roomRepository.save(room);
        LocalDate today = LocalDate.now();
        LocalDate weekAway = today.plus(7, ChronoUnit.DAYS);
        LocalDate twoWeeksAway = weekAway.plus(7, ChronoUnit.DAYS);
        LocalDate threeWeeksAway = twoWeeksAway.plus(7, ChronoUnit.DAYS);

        RoomRegistry user1RoomRegistry1 = new RoomRegistry(room, today, weekAway,user1);
        RoomRegistry user1RoomRegistry2 = new RoomRegistry(room, weekAway, twoWeeksAway,user1);
        RoomRegistry user1RoomRegistry3 = new RoomRegistry(room, twoWeeksAway.plus(3, ChronoUnit.DAYS), twoWeeksAway.plus(7, ChronoUnit.DAYS),user1);
        RoomRegistry user2RoomRegistry1 = new RoomRegistry(room, threeWeeksAway.plus(8, ChronoUnit.DAYS), threeWeeksAway.plus(10, ChronoUnit.DAYS),user2);
        RoomRegistry user2RoomRegistry2 = new RoomRegistry(room, threeWeeksAway.plus(11, ChronoUnit.DAYS), threeWeeksAway.plus(14, ChronoUnit.DAYS),user2);
        roomRegistryRepository.save(user1RoomRegistry1);
        roomRegistryRepository.save(user1RoomRegistry2);
        roomRegistryRepository.save(user1RoomRegistry3);
        roomRegistryRepository.save(user2RoomRegistry1);
        roomRegistryRepository.save(user2RoomRegistry2);

        adminRoomService.closeRoom(new CloseRoomForm(today, threeWeeksAway.plus(16, ChronoUnit.DAYS), room.getId()));

        User dbUser1 = userRepository.getById(user1.getId());
        User dbUser2 = userRepository.getById(user2.getId());
        assertEquals(new BigDecimal("600.00"), dbUser2.getBalance());
        assertEquals(new BigDecimal("1800.00"), dbUser1.getBalance());
    }

}

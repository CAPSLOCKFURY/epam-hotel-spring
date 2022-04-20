package com.example.epamhotelspring.service;

import com.example.epamhotelspring.dto.RoomRequestDTO;
import com.example.epamhotelspring.fixtures.RoomRequestDataGenerator;
import com.example.epamhotelspring.forms.RoomRequestForm;
import com.example.epamhotelspring.model.*;
import com.example.epamhotelspring.model.enums.RequestStatus;
import com.example.epamhotelspring.repository.*;
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

    @Autowired
    private BillingRepository billingRepository;

    private static User user;

    private static Room room;

    private static RoomClass roomClass;

    @BeforeAll
    public void setUp(){
        User user = new User("roomsTester", "password", "roomTester@gmail.com", "Room", "Roomich").setBalance(new BigDecimal(100));
        RoomRequestServiceTest.user = userRepository.save(user);
        RoomClass roomClass = new RoomClass();
        RoomClassTranslation rct = new RoomClassTranslation(roomClass, "en", "someClassName");
        RoomRequestServiceTest.roomClass = roomClassRepository.save(roomClass);
        rctRepository.save(rct);
        Room room = new Room(100, String.valueOf(100), 100, new BigDecimal(100 * 2), roomClass);
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

    @Test
    void getUserRoomRequestsTest(){
        int requestsCount = 5;
        Iterable<? extends RoomRequest> roomRequests = RoomRequestDataGenerator.generateRoomRequests(user, roomClass, requestsCount);
        roomRequestRepository.saveAll(roomRequests);
        Pageable pageable = Pageable.ofSize(10);
        Page<RoomRequestDTO> userRoomRequests = roomRequestService.getUserRoomRequests(user.getId(), pageable);
        assertEquals(requestsCount, userRoomRequests.getNumberOfElements());
    }

    @Test
    void closeRoomRequestTest(){
        LocalDate today = LocalDate.now();
        LocalDate todayPlus7 = today.plus(7, ChronoUnit.DAYS);
        RoomRequest roomRequest = new RoomRequest(user, roomClass, 2, today, todayPlus7);
        roomRequest = roomRequestRepository.save(roomRequest);
        roomRequestService.closeRoomRequest(roomRequest.getId(), user.getId());
        roomRequest = roomRequestRepository.getById(roomRequest.getId());
        assertEquals(RequestStatus.CLOSED, roomRequest.getStatus());
    }

    @Test
    void acceptRoomRequestTest(){
        LocalDate today = LocalDate.now();
        LocalDate todayPlus7 = today.plus(7, ChronoUnit.DAYS);
        RoomRequest roomRequest = new RoomRequest(user, roomClass, 2, today, todayPlus7).setRoom(room).setStatus(RequestStatus.AWAITING_CONFIRMATION);
        roomRequest = roomRequestRepository.save(roomRequest);
        roomRequestService.acceptRoomRequest(roomRequest.getId(), user.getId());
        Pageable pageable = Pageable.ofSize(10);
        roomRequest = roomRequestRepository.getById(roomRequest.getId());
        assertEquals(RequestStatus.AWAITING_PAYMENT, roomRequest.getStatus());
        Page<Billing> assignedBillings = billingRepository.findBillingsByRoomRequest_UserId(user.getId(), pageable);
        assertEquals(1, assignedBillings.getNumberOfElements());
    }

    @Test
    void declineRoomRequestTest(){
        LocalDate today = LocalDate.now();
        LocalDate todayPlus7 = today.plus(7, ChronoUnit.DAYS);
        RoomRequest roomRequest = new RoomRequest(user, roomClass, 2, today, todayPlus7).setRoom(room).setStatus(RequestStatus.AWAITING);
        roomRequest = roomRequestRepository.save(roomRequest);
        roomRequestService.closeRoomRequest(roomRequest.getId(), user.getId());
        roomRequest = roomRequestRepository.getById(roomRequest.getId());
        assertEquals(RequestStatus.CLOSED, roomRequest.getStatus());
    }

}
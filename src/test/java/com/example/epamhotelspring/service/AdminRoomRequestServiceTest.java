package com.example.epamhotelspring.service;

import com.example.epamhotelspring.dto.AdminRoomRequestDTO;
import com.example.epamhotelspring.forms.CloseRequestForm;
import com.example.epamhotelspring.model.Room;
import com.example.epamhotelspring.model.RoomClass;
import com.example.epamhotelspring.model.RoomRequest;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.model.enums.RequestStatus;
import com.example.epamhotelspring.repository.RoomClassRepository;
import com.example.epamhotelspring.repository.RoomRepository;
import com.example.epamhotelspring.repository.RoomRequestRepository;
import com.example.epamhotelspring.repository.UserRepository;
import com.example.epamhotelspring.service.admin.AdminRoomRequestService;
import com.example.epamhotelspring.service.utils.ServiceErrors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
@TestPropertySource(locations = "/application-test.properties")
public class AdminRoomRequestServiceTest {

    @Autowired
    private AdminRoomRequestService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomClassRepository roomClassRepository;

    @Autowired
    private RoomRequestRepository roomRequestRepository;

    private long roomRequestId;

    private long roomId;

    void setUp() {
        LocalDate today = LocalDate.now();
        LocalDate weekAway = today.plus(7, ChronoUnit.DAYS);
        User user = new User("AdminRoomRequestServiceTest", "123", "AdminRoomRequestServiceTest@gmail.com", "AdminRoomRequestServiceTest", "AdminRoomRequestServiceTest");
        userRepository.save(user);
        RoomClass roomClass = new RoomClass();
        roomClassRepository.save(roomClass);
        Room room = new Room(1, "Room 1", 2, new BigDecimal(100), roomClass);
        roomRepository.save(room);
        roomId = room.getId();
        RoomRequest roomRequest = new RoomRequest(user, roomClass, 2, today, weekAway);
        roomRequestRepository.save(roomRequest);
        roomRequestId = roomRequest.getId();
    }

    @Test
    void getAdminRoomRequestsTest() {
        setUp();
        assertEquals(1, service.getAdminRoomRequests("AWAITING", PageRequest.of(0, 10)).getNumberOfElements());
    }

    @Test
    void getAdminRoomRequestByIdTest() {
        setUp();
        AdminRoomRequestDTO roomRequest = service.getAdminRoomRequestById(roomRequestId);
        assertEquals(roomRequestId, roomRequest.getId());
    }

    @Test
    void assignRoomToRequestTest(){
        setUp();
        ServiceErrors serviceErrors = new ServiceErrors();
        service.assignRoomToRequest(roomRequestId, roomId, serviceErrors);
        assertEquals(0, serviceErrors.getErrors().size());
        RoomRequest roomRequest = roomRequestRepository.getById(roomRequestId);
        assertEquals(roomId, roomRequest.getRoom().getId());
        assertEquals(RequestStatus.AWAITING_CONFIRMATION, roomRequest.getStatus());
    }

    @Test
    void closeRoomRequestTest(){
        setUp();
        String managerComment = "sample comment";
        ServiceErrors serviceErrors = new ServiceErrors();
        service.closeRoomRequest(new CloseRequestForm(managerComment, roomRequestId), serviceErrors);
        assertEquals(0, serviceErrors.getErrors().size());
        RoomRequest closedRoomRequest = roomRequestRepository.getById(roomRequestId);
        assertEquals(RequestStatus.CLOSED, closedRoomRequest.getStatus());
        assertEquals(managerComment, closedRoomRequest.getManagerComment());
    }

}

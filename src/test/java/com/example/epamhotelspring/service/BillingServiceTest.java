package com.example.epamhotelspring.service;

import com.example.epamhotelspring.model.*;
import com.example.epamhotelspring.model.enums.RequestStatus;
import com.example.epamhotelspring.repository.*;
import com.example.epamhotelspring.service.utils.ServiceErrors;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@TestPropertySource(locations = "/application-test.properties")
public class BillingServiceTest {

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private BillingService billingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomRequestRepository roomRequestRepository;

    @Autowired
    private RoomClassRepository roomClassRepository;

    private static User user;

    @BeforeAll
    public void setUp(){
        User user = new User("billingTester", "password", "billingTester@gmail.com", "Billing", "Billingov");
        user.setBalance(new BigDecimal(100000));
        BillingServiceTest.user = userRepository.save(user);
    }

    @Test
    void payBillingTest(){
        RoomClass roomClass = new RoomClass();
        roomClassRepository.save(roomClass);
        Room room = new Room(998, String.valueOf(998), 998, new BigDecimal(100),  roomClass);
        roomRepository.save(room);
        LocalDate today = LocalDate.now();
        LocalDate todayPlus7 = today.plus(7, ChronoUnit.DAYS);
        RoomRequest roomRequest = new RoomRequest(user, roomClass, 2, today, todayPlus7).setStatus(RequestStatus.AWAITING_PAYMENT).setRoom(room);
        roomRequestRepository.save(roomRequest);
        Billing billing = new Billing(roomRequest);
        billing = billingRepository.save(billing);


        ServiceErrors serviceErrors = new ServiceErrors();

        billingService.payBilling(billing.getId(), user, serviceErrors);

        assertTrue(serviceErrors.getErrors().isEmpty());

        billing = billingRepository.getById(billing.getId());
        assertTrue(billing.getPaid());
    }

}

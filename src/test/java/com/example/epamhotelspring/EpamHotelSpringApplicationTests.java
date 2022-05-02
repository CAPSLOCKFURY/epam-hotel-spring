package com.example.epamhotelspring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
class EpamHotelSpringApplicationTests {

    @Test
    void contextLoads() {
    }

}

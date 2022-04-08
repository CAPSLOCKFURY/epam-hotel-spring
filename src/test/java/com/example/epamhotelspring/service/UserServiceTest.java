package com.example.epamhotelspring.service;

import com.example.epamhotelspring.forms.UserUpdateForm;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(locations = "/application-test.properties")
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeAll
    public void setUp(){
        User user = new User().setUsername("username").setPassword("password").setEmail("email@gmail.com")
                .setFirstName("test").setLastName("testov");
        userRepository.save(user);
    }

    @Test
    void loadUserByUsernameTest(){
        String username = "username";
        User user = (User) userService.loadUserByUsername(username);
        assertNotNull(user.getId());
        assertEquals(username, user.getUsername());

        String nonExistingUsername = "nonExistingUsername_";
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(nonExistingUsername));
    }

    @Test
    void registerUserTest(){
        User newUser = new User().setUsername("test0user").setPassword("12345").setEmail("testemail@gmail.com")
                .setFirstName("John").setLastName("Doe");
        User returnedUser = userService.registerUser(newUser);
        assertNotNull(returnedUser);
        assertNotNull(returnedUser.getId());
    }

    @Test
    void getUserByEmailTest(){
        String mail = "email@gmail.com";
        User user = userService.getUserByEmail(mail);
        assertNotNull(user);
        assertEquals(mail, user.getEmail());
    }

    @Test
    void getUserByIdTest(){
        Long id = 1L;
        User user = userService.getUserById(id);
        assertNotNull(user);
        assertEquals(id, user.getId());
    }

    @Test
    void addBalanceTest(){
        User newUser = new User().setUsername("test1user").setPassword("12345").setEmail("testemail1@gmail.com")
                .setFirstName("John1").setLastName("Doe1");
        User dbUser = userRepository.save(newUser);
        userService.addBalance(new BigDecimal(100), dbUser);
        dbUser = userRepository.findUserById(dbUser.getId());
        assertEquals(0, dbUser.getBalance().compareTo(new BigDecimal(100)));
    }

    @Test
    void updateUserTest(){
        String updFirstName = "UpdJohn";
        String updLastName = "UpdDoe";
        UserUpdateForm form = new UserUpdateForm(updFirstName, updLastName);
        userService.updateProfile(form, 1L);
        User dbUser = userRepository.findUserById(1L);
        assertNotNull(dbUser);
        assertEquals(dbUser.getFirstName(), updFirstName);
        assertEquals(dbUser.getLastName(), updLastName);
    }

}

package com.bloomtech.userCreation.services;

import com.bloomtech.userCreation.App;
import com.bloomtech.userCreation.models.User;
import com.bloomtech.userCreation.repositories.UserDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = App.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void createUser() {
        User newUser = User.builder()
                .withUsername("testUser")
                .withPassword("TestPassword1!")
                .withEmail("test@email.com")
                .build();
        assertNotNull(userService.createUser(newUser));
    }

    @Test
    void createUserInvalidUsername() {
        User newUser = User.builder()
                .withUsername("bad-username")
                .withPassword("$0kayPassword")
                .withEmail("badun@email.com")
                .build();
        assertNull(userService.createUser(newUser));
    }

    @Test
    void createUserInvalidPassword() {
        User newUser = User.builder()
                .withUsername("okayUsername")
                .withPassword("bad password")
                .withEmail("badpw@email.com")
                .build();
        assertNull(userService.createUser(newUser));
    }

    @Test
    void createUserInvalidEmailDomain() {
        User newUser = User.builder()
                .withUsername("okayUsername")
                .withPassword("$0kayPassword")
                .withEmail("bademail.com")
                .build();
        assertNull(userService.createUser(newUser));
    }

    @Test
    void createUserInvalidEmailDomainIdentifier() {
        User newUser = User.builder()
                .withUsername("okayUsername")
                .withPassword("$0kayPassword")
                .withEmail("bademail@nothing")
                .build();
        assertNull(userService.createUser(newUser));
    }

    @Test
    void getUsers() {
        //This test will only pass if createUser runs successfully first
        assertEquals(1, userService.getUsersList().size());
    }
}
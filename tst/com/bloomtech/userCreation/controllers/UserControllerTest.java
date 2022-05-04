package com.bloomtech.userCreation.controllers;

import com.bloomtech.userCreation.models.Role;
import com.bloomtech.userCreation.models.User;
import com.bloomtech.userCreation.repositories.UserDataRepository;
import com.bloomtech.userCreation.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> userList;

    @BeforeEach
    public void setUp() throws Exception {
        userList = new ArrayList<>();

        User u1 = User.builder()
                .withUsername("testUser1")
                .withPassword("TestPassw0rd!")
                .withEmail("test@email.com")
                .withInfo("test user 1 info")
                .withAllowSlackIntegration(true)
                .build();
        User u2 = User.builder()
                .withUsername("testUser2")
                .withPassword("TestPassw0rd!")
                .withEmail("test2@email.com")
                .withInfo("test user 2 info")
                .withRole(Role.COORDINATOR)
                .withAllowSlackIntegration(true)
                .build();

        userList.add(u1);
        userList.add(u2);
    }

    @Test
    void getUsers() throws Exception {
        String apiUrl = "/users";

        //Have Mockito return the usersList instead of the actual database users
        Mockito.when(userService.getUsersList()).thenReturn(userList);

        //Build a request to GET from apiUrl and to expect JSON back.
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        //Perform the request
        MvcResult r = mockMvc.perform(rb).andReturn();

        //Store result as a String
        String testResult = r.getResponse().getContentAsString();

        //Use Jackson ObjectMapper to turn our expected JSON into a string for comparison
        ObjectMapper mapper = new ObjectMapper();
        String ex = mapper.writeValueAsString(userList);

        assertEquals(ex, testResult);
    }

    @Test
    void addNewUser() throws Exception {
        String apiUrl = "/users";

        User user = User.builder()
                .withUsername("newUser")
                .withPassword("TestPassw0rd!")
                .withEmail("test@email.com")
                .withInfo("post user info")
                .withRole(Role.ADMIN)
                .withAllowSlackIntegration(true)
                .build();


        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(user);

        Mockito.when(userService.createUser(any(User.class))).thenReturn(user);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb)
                .andExpect(status().isCreated())
                .andExpect(content().string(userString));
    }

    @Test
    void addUser_BadUserInfo() throws Exception {
        String apiUrl = "/users";

        User user = User.builder()
                .withUsername("bad username")
                .withPassword("bad password")
                .withEmail("bad email")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(user);

        Mockito.when(userService.createUser(any(User.class))).thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb)
                .andExpect(status().isBadRequest());
    }
}
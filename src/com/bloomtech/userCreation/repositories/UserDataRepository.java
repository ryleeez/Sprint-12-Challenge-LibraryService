package com.bloomtech.userCreation.repositories;

import com.bloomtech.userCreation.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDataRepository {
    private static final List<User> users  = new ArrayList<>();

    public UserDataRepository() {
    }

    public List<User> getUsersList() {
        return users;
    }

    public boolean checkUsernameExists(String username) {
        return users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }

    public User createUser(User user) {
        User newUser = User.builder()
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withInfo(user.getInfo())
                .withAllowSlackIntegration(user.isAllowSlackIntegration())
                .withRole(user.getRole())
                .withPassword(user.getPassword())
                .build();
        users.add(newUser);
        return newUser;
    }
}

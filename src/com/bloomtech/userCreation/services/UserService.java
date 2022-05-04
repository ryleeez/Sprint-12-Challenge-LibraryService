package com.bloomtech.userCreation.services;

import com.bloomtech.userCreation.models.User;
import com.bloomtech.userCreation.repositories.UserDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public class UserService {
    public static final UserDataRepository userDataManager = new UserDataRepository();
    private static final ValidationService validationService = new ValidationService(userDataManager);

    public UserService() {
    }

    public User createUser(User user) {
        if (!validationService.validateUserInfo(user)) return null;

        return userDataManager.createUser(user);
    }

    public List<User> getUsersList() {
        return userDataManager.getUsersList();
    }
}

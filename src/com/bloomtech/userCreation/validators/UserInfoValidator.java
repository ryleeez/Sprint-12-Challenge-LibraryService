package com.bloomtech.userCreation.validators;

import com.bloomtech.userCreation.models.Role;
import com.bloomtech.userCreation.models.User;
import com.bloomtech.userCreation.repositories.UserDataRepository;
import com.bloomtech.userCreation.services.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInfoValidator implements Validator {
    UserDataRepository userDataRepository;

    public UserInfoValidator(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    private boolean isValidUsername(String username) {
        if (username == null) return false;

        if (Pattern.compile("[\\s\\W]").matcher(username).find()) return false;

        if (userDataRepository.checkUsernameExists(username)) return false;
        return true;
    }

    private boolean isValidPassword(String password) {
        if (password == null) return false;
        password = password.trim();
        if (!Pattern.compile("(?=.*[A-Z])(?=.*[\\W])[\\S]{8,}").matcher(password).find()) return false;
        return true;
    }

    @Override
    public boolean validate(Object userData) {
        if (userData.getClass() != User.class) return false;
        User user = (User) userData;

        if (!isValidUsername(user.getUsername())) {
            System.out.println("Invalid Username: Username must not contain special characters or spaces!");
            return false;
        }
        if (!isValidPassword(user.getPassword())) {
            System.out.println("Invalid Password: Password must be at least 8 characters long, " +
                    "contain at least one uppercase letter, one lowercase letter, and one special character!");
            return false;
        }
        if (user.getRole() == null) { user.setRole(Role.USER); }

        return true;
    }
}

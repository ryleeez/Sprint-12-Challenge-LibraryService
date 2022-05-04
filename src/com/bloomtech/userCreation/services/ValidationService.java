package com.bloomtech.userCreation.services;

import com.bloomtech.userCreation.models.User;
import com.bloomtech.userCreation.repositories.UserDataRepository;
import com.bloomtech.userCreation.validators.EmailValidator;
import com.bloomtech.userCreation.validators.UserInfoValidator;

public class ValidationService {

    private UserInfoValidator userInfoValidator;
    private EmailValidator emailValidator;

    public ValidationService(UserDataRepository userDataRepository) {
        userInfoValidator = new UserInfoValidator(userDataRepository);
        emailValidator = new EmailValidator();
    }

    public boolean validateUserInfo(User user) {
        return userInfoValidator.validate(user) && emailValidator.validate(user.getEmail());
    }
}

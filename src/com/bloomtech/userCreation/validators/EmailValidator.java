package com.bloomtech.userCreation.validators;

import java.util.regex.Pattern;

public class EmailValidator implements Validator {
    public EmailValidator() {
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;
        if (!Pattern.compile("^[\\w_.-]+@[\\w]+\\.[\\w]+$").matcher(email).find()) return false;
        return true;
    }

    @Override
    public boolean validate(Object emailData) {
        if (emailData.getClass() != String.class) return false;
        String email = (String) emailData;
        if (!isValidEmail(email)) {
            System.out.println("Invalid Email: Email address must include '@' before domain and a domain identifier after a '.'!");
            return false;
        }
        return true;
    }
}

package com.assignment.users.service;

import com.assignment.users.bo.CreateUserBO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class PasswordValidator {
    private static List<String> commonWords = Arrays.asList("password", "123456", "qwerty", "letmein", "welcome");

    public  String validatePassword(String password, String username) {
        String errorMessage = "";
        if (isCommonPassword(password)) {
            errorMessage = "Password should not be a common word";
        } else if (password.toLowerCase().contains(username.toLowerCase())) {
            errorMessage = "Password should not contain the username";
        }else if (password.length() < 8 || password.length() > 24) {
            errorMessage = "Choose password to be between 8 and 24 characters";
        } else if (!password.matches(".*[A-Z].*")) {
            errorMessage = "Password must contain at least one uppercase letter";
        } else if (!password.matches(".*[a-z].*")) {
            errorMessage = "Password must contain at least one lowercase letter";
        } else if (!password.matches(".*\\d.*")) {
            errorMessage = "Password must contain at least one digit";
        } else if (!password.matches(".*[^A-Za-z0-9].*")) {
            errorMessage = "Password must contain at least one special character";
        }
        return errorMessage;
    }

    private  boolean isCommonPassword(String password) {
        return commonWords.stream().anyMatch(commonWord -> commonWord.contains(password));
    }


}

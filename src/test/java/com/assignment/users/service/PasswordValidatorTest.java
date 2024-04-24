package com.assignment.users.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PasswordValidatorTest {

    private PasswordValidator passwordValidator = new PasswordValidator();

    @Test
    public void testValidatePassword_InvalidLength_ReturnsErrorMessage() {
        
        String password = "short";
        String username = "username";

        
        String errorMessage = passwordValidator.validatePassword(password, username);

        
        assertEquals("Choose password to be between 8 and 24 characters", errorMessage);
    }

    @Test
    public void testValidatePassword_ValidLength_NoErrorMessage() {
        
        String password = "validPassword$123";
        String username = "username";

        String errorMessage = passwordValidator.validatePassword(password, username);

        
        assertEquals("", errorMessage);
    }

    @Test
    public void testValidatePassword_NoUpperCase_ReturnsErrorMessage() {
        
        String password = "nopassword123";
        String username = "username";
        
        String errorMessage = passwordValidator.validatePassword(password, username);

        
        assertEquals("Password must contain at least one uppercase letter", errorMessage);
    }

    @Test
    public void testValidatePassword_NoUppercaseLetter_ReturnsErrorMessage() {
        
        String password = "nouppercase123";
        String username = "username";

        
        String errorMessage = passwordValidator.validatePassword(password, username);

        assertEquals("Password must contain at least one uppercase letter", errorMessage);
    }

    @Test
    public void testValidatePassword_NoLowercaseLetter_ReturnsErrorMessage() {
        
        String password = "NOLOWERCASE123";
        String username = "username";

        String errorMessage = passwordValidator.validatePassword(password, username);
        
        assertEquals("Password must contain at least one lowercase letter", errorMessage);
    }

    @Test
    public void testValidatePassword_NoDigit_ReturnsErrorMessage() {
        
        String password = "NoDigit!";
        String username = "username";

        String errorMessage = passwordValidator.validatePassword(password, username);
        assertEquals("Password must contain at least one digit", errorMessage);
    }

    @Test
    public void testValidatePassword_NoSpecialCharacter_ReturnsErrorMessage() {
        
        String password = "NoSpecialCharacter123";
        String username = "username";
        
        String errorMessage = passwordValidator.validatePassword(password, username);
        assertEquals("Password must contain at least one special character", errorMessage);
    }

    @Test
    public void testValidatePassword_ContainsUsername_ReturnsErrorMessage() {
        
        String password = "username123";
        String username = "username";
        String errorMessage = passwordValidator.validatePassword(password, username);
        assertEquals("Password should not contain the username", errorMessage);
    }

    @Test
    public void testValidatePassword_IsCommonPassword_ReturnsErrorMessage() {
        
        String password = "password";
        String username = "username";
        String errorMessage = passwordValidator.validatePassword(password, username);
        assertEquals("Password should not be a common word", errorMessage);
    }

}

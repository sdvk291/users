package com.assignment.users.service;

import com.assignment.users.bo.CreateUserBO;
import com.assignment.users.bo.UpdateUserBO;
import com.assignment.users.dao.UserRepository;
import com.assignment.users.dto.UserDTO;
import com.assignment.users.model.User;
import com.assignment.users.request.CreateUserRequest;
import com.assignment.users.request.UpdateUserRequest;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserServiceHelper userServiceHelper;

    @Mock
    private PasswordValidator passwordValidator;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser_WithValidRequest_Success() {
        
        CreateUserRequest request = new CreateUserRequest("wolf", "play@witcher.com", "Geralt", "Rivia", "password");
        when(passwordValidator.validatePassword(anyString(), anyString())).thenReturn("");
        when(userServiceHelper.isUsernameAvailable(anyString())).thenReturn(true);
        User user = new User();
        user.setUsername("username");
        user.setEmailId("play@witcher.com");
        user.setFirstName("Geralt");
        user.setLastName("Rivia");
        user.setPassword("password");
        when(userServiceHelper.createUser(any(User.class))).thenReturn(user);
        CreateUserBO result = userService.createUser(request);
        assertTrue(result.isSuccess());
        assertEquals("wolf", result.getUserDTO().getUsername());
    }

    @Test
    public void testCreateUser_WithInvalidPassword_Failure() {
        
        CreateUserRequest request = new CreateUserRequest("username", "play@witcher.com", "Geralt", "Rivia", "short");
        when(passwordValidator.validatePassword(anyString(),anyString())).thenReturn("Password too short");

        CreateUserBO result = userService.createUser(request);

        assertFalse(result.isSuccess());
        assertEquals("Password too short", result.getErrorMessage());
    }


    @Test
    public void testGetUser_ExistingUser_ReturnsUserDTO() {
        
        when(userRepository.findById("wolf")).thenReturn(Optional.of(new User("wolf", "play@witcher.com", "Geralt", "Rivia", "password")));
        
        Optional<UserDTO> result = userService.getUser("wolf");

        
        assertTrue(result.isPresent());
        assertEquals("wolf", result.get().getUsername());
    }

    @Test
    public void testGetUser_NonExistingUser_ReturnsEmptyOptional() {
        
        when(userRepository.findById("nonexisting")).thenReturn(Optional.empty());
        Optional<UserDTO> result = userService.getUser("nonexisting");
        assertFalse(result.isPresent());
    }


    @Test
    public void testDeleteUser_ExistingUser_DeletesUser() {
        
        userService.deleteUser("username");
        verify(userRepository).deleteById("username");
    }


    @Test
    public void testUpdateUser_WithValidRequest_Success() {
        UpdateUserRequest request = new UpdateUserRequest("username", "play@witcher.com", "Yo", "Update", "password");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User existingUser = new User("username", "play@witcher.com", "Geralt", "Witcher",encoder.encode("password") );
        when(passwordValidator.validatePassword(anyString(), anyString())).thenReturn("");
        when(userServiceHelper.getUser("username")).thenReturn(Optional.of(existingUser));
        UpdateUserBO result = userService.updateUser(request);
        assertTrue(result.isSuccess());
        assertEquals("username", result.getUserDTO().getUsername());
        assertEquals("play@witcher.com", result.getUserDTO().getEmailId());
        assertEquals("Yo", result.getUserDTO().getFirstName());
        assertEquals("Update", result.getUserDTO().getLastName());
    }

    @Test
    public void testUpdateUser_InvalidRequest_ReturnsErrorMessage() {
        // Setup
        UpdateUserRequest request = new UpdateUserRequest("vk", "vk@rcb.com", "vk", "de", "password");
        when(passwordValidator.validatePassword(anyString(), anyString())).thenReturn("Invalid password");

        // Execute
        UpdateUserBO updateUserBO = userService.updateUser(request);

        // Verify
        assertFalse(updateUserBO.isSuccess());
        assertEquals("Invalid password", updateUserBO.getErrorMessage());
        verify(userServiceHelper, never()).getUser(anyString());
    }

    @Test
    public void testUpdateUser_WrongPassword_ReturnsErrorMessage() {
        // Setup
        UpdateUserRequest request = new UpdateUserRequest("username", "email@example.com", "John", "Doe", "newpassword");
        User existingUser = new User("username", "email@example.com", "John", "Doe", "password");
        when(userServiceHelper.getUser("username")).thenReturn(Optional.of(existingUser));
        when(passwordValidator.validatePassword("newpassword", "username")).thenReturn("");

        // Execute
        UpdateUserBO updateUserBO = userService.updateUser(request);

        // Verify
        assertFalse(updateUserBO.isSuccess());
        assertEquals("you don't have access to update", updateUserBO.getErrorMessage());
        verify(userServiceHelper, times(1)).getUser("username");
        verify(userServiceHelper, never()).createUser(any());

    }
}

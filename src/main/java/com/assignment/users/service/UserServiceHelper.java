package com.assignment.users.service;

import com.assignment.users.dao.UserRepository;
import com.assignment.users.dto.UserDTO;
import com.assignment.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceHelper {
    private UserRepository userRepository;

    @Autowired
    public UserServiceHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean isUsernameAvailable(String username){
        return !userRepository.existsById(username);
    }

    @Transactional
    public Optional<User> getUser(String username){
        return userRepository.findById(username);
    }

    @Transactional
    public User createUser(User user){
        return userRepository.save(user);
    }

}

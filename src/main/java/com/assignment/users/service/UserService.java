package com.assignment.users.service;

import com.assignment.users.bo.CreateUserBO;
import com.assignment.users.dao.UserRepository;
import com.assignment.users.dto.UserDTO;
import com.assignment.users.model.User;
import com.assignment.users.request.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private UserRepository userRepository;
    private UserServiceHelper userServiceHelper;

    private PasswordValidator passwordValidator;

    @Autowired
    public UserService(UserRepository userRepository, UserServiceHelper userServiceHelper,
                       PasswordValidator passwordValidator) {
        this.userRepository = userRepository;
        this.userServiceHelper = userServiceHelper;
        this.passwordValidator = passwordValidator;
    }


    public CreateUserBO createUser(CreateUserRequest request){
        CreateUserBO createUserBO = new CreateUserBO();
        passwordValidator.validatePassword(request.getPassword(), request.getUsername(), createUserBO);
        if(!createUserBO.isSuccess()){
            return createUserBO;
        }
        if(!userServiceHelper.isUsernameAvailable(request.getUsername())){
            createUserBO.setErrorMessage(String.format("%s is unavailable",request.getUsername()));
            createUserBO.setSuccess(false);
            return createUserBO;
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmailId(request.getEmailId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userServiceHelper.createUser(user);
        createUserBO.setUserDTO(user.toDTO());
        return createUserBO;
    }

}

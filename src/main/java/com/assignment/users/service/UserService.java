package com.assignment.users.service;

import com.assignment.users.bo.CreateUserBO;
import com.assignment.users.bo.UpdateUserBO;
import com.assignment.users.dao.UserRepository;
import com.assignment.users.dto.UserDTO;
import com.assignment.users.model.User;
import com.assignment.users.request.CreateUserRequest;
import com.assignment.users.request.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserServiceHelper userServiceHelper;

    private final PasswordValidator passwordValidator;



    @Autowired
    public UserService(UserRepository userRepository, UserServiceHelper userServiceHelper, PasswordValidator passwordValidator) {
        this.userRepository = userRepository;
        this.userServiceHelper = userServiceHelper;

        this.passwordValidator = passwordValidator;
    }


    public CreateUserBO createUser(CreateUserRequest request){
        CreateUserBO createUserBO = new CreateUserBO();
        String errorMessage = passwordValidator.validatePassword(request.getPassword(), request.getUsername());
        createUserBO.setSuccess(errorMessage.length() == 0);
        createUserBO.setErrorMessage(errorMessage);
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
        user.setPassword(request.getPassword());
        userServiceHelper.createUser(user);
        createUserBO.setUserDTO(user.toDTO());
        return createUserBO;
    }


    public Optional<UserDTO> getUser(String username){
        return userRepository.findById(username).map(User::toDTO);
    }

    public void deleteUser(String username){
         userRepository.deleteById(username);
    }
    @Transactional
    public UpdateUserBO updateUser(UpdateUserRequest request){
        UpdateUserBO updateUserBO = new UpdateUserBO();
        String errorMessage = passwordValidator.validatePassword(request.getPassword(), request.getUsername());
        updateUserBO.setSuccess(errorMessage.length()==0);
        updateUserBO.setErrorMessage(errorMessage);
        if(!updateUserBO.isSuccess()){
            return updateUserBO;
        }
        Optional<User> existingUserOptional = userServiceHelper.getUser(request.getUsername());
        if(existingUserOptional.isEmpty()){
            updateUserBO.setErrorMessage(String.format("%s doesn't exist",request.getUsername()));
            updateUserBO.setSuccess(false);
            return updateUserBO;
        }
        User user = existingUserOptional.get();
        if(!user.getPassword().equals(request.getPassword())){
            updateUserBO.setErrorMessage("you don't have access to update");
            updateUserBO.setSuccess(false);
            return updateUserBO;
        }
        user.setEmailId(request.getEmailId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        updateUserBO.setUserDTO(user.toDTO());
        return updateUserBO;
    }

}

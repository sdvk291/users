package com.assignment.users.controller;

import com.assignment.users.bo.CreateUserBO;
import com.assignment.users.dto.UserDTO;
import com.assignment.users.request.CreateUserRequest;
import com.assignment.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private  UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CreateUserRequest createUserRequest){
        CreateUserBO createUserBO = userService.createUser(createUserRequest);
        if(createUserBO.isSuccess()){
            return ResponseEntity.ok(createUserBO.getUserDTO());
        }else{
            return ResponseEntity.badRequest().body(createUserBO.getErrorMessage());
        }
    }
}

package com.assignment.users.controller;

import com.assignment.users.bo.CreateUserBO;
import com.assignment.users.bo.UpdateUserBO;
import com.assignment.users.dto.UserDTO;
import com.assignment.users.request.CreateUserRequest;
import com.assignment.users.request.UpdateUserRequest;
import com.assignment.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private  UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateUserRequest createUserRequest){
        CreateUserBO createUserBO = userService.createUser(createUserRequest);
       System.out.println(createUserBO.getUserDTO());
        if(createUserBO.isSuccess()){
            return ResponseEntity.ok(createUserBO.getUserDTO());
        }else{
            return ResponseEntity.badRequest().body(createUserBO.getErrorMessage());
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username){
       Optional<UserDTO> userDTOOptional = userService.getUser(username);
        if(userDTOOptional.isPresent()){
            return ResponseEntity.ok(userDTOOptional.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username){
       userService.deleteUser(username);
       return ResponseEntity.ok("success");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request){
        UpdateUserBO updateUserBO = userService.updateUser(request);
        if(updateUserBO.isSuccess()){
            return ResponseEntity.ok(updateUserBO.getUserDTO());
        }else{
            return ResponseEntity.badRequest().body(updateUserBO.getErrorMessage());
        }
    }

}

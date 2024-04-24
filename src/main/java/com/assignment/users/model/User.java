package com.assignment.users.model;


import com.assignment.users.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "x_user")
public class User {
    @Id
    private String username;
    private String emailId;
    private String firstName;
    private String lastName;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserDTO toDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmailId(this.getEmailId());
        userDTO.setFirstName(this.getFirstName());
        userDTO.setLastName(this.getLastName());
        userDTO.setUsername(this.getUsername());
        return userDTO;
    }
}

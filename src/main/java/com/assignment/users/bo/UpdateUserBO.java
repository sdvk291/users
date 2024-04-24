package com.assignment.users.bo;

import com.assignment.users.dto.UserDTO;

public class UpdateUserBO {
    private boolean isSuccess;
    private UserDTO userDTO;
    private String errorMessage;

    public UpdateUserBO() {
    }

    public UpdateUserBO(boolean isSuccess, UserDTO userDTO, String errorMessage) {
        this.isSuccess = isSuccess;
        this.userDTO = userDTO;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

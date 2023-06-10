package com.example.backend_prj.exception;

import com.example.backend_prj.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "already exists")
public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message){
        super(message);
    }
    public UserAlreadyExistException(String message, Throwable cause){
        super(message,cause);
    }
}

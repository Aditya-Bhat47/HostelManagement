package com.example.HostelManagement.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundexception extends RuntimeException{
    public ResourceNotFoundexception(String message){
        super(message);
    }
}

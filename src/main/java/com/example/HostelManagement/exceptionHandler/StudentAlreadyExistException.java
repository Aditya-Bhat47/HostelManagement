package com.example.HostelManagement.exceptionHandler;

public class StudentAlreadyExistException extends RuntimeException{

    public StudentAlreadyExistException(String message){
        super(message);
    }
}

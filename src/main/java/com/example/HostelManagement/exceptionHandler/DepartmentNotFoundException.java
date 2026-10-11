package com.example.HostelManagement.exceptionHandler;

public class DepartmentNotFoundException extends RuntimeException{

    public DepartmentNotFoundException(String message){
        super(message);
    }
}

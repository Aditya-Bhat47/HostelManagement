package com.example.HostelManagement.controller;

import com.example.HostelManagement.records.StudentRegistrationRequest;
import com.example.HostelManagement.records.StudentRegistrationResponse;
import com.example.HostelManagement.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<StudentRegistrationResponse> registerStudent(
            @Valid @RequestBody StudentRegistrationRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.registerStudent(request));
    }
}
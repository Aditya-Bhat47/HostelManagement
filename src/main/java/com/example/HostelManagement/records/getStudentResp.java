package com.example.HostelManagement.records;

import com.example.HostelManagement.entity.Department;
import com.example.HostelManagement.entity.Rooms;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record getStudentResp(

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be 2 to 50 characters")
        @Pattern(regexp = "^[A-Za-z ]+$", message = "Name can contain only letters and spaces")
        String name,

        @NotBlank(message = "USN is required")
        @Pattern(regexp = "^[1-4][A-Za-z]{2}[0-9]{2}[A-Za-z]{2,3}[0-9]{3}$",
                message = "Enter a valid USN (e.g. 1AB21CS001)")
        String usn,

        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email")
        @Size(max = 100)
        String email,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^[6-9][0-9]{9}$",
                message = "Enter a valid 10-digit Indian mobile number")
        String phone,

        @NotNull(message = "Department is required")
        Department department,

        @NotNull(message = "Fees is required")
        @DecimalMin(value = "0.0", message = "Fees cannot be negative")
        @Digits(integer = 8, fraction = 2, message = "Invalid fees format")
        BigDecimal fees,

        @NotNull
        @PositiveOrZero(message = "Pending amount cannot be negative")
        @Digits(integer = 8, fraction = 2)
        BigDecimal pending,

        @NotNull(message = "Room is required")
        @Valid
        Rooms room
) {}

package com.example.HostelManagement.records;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record StudentRequest(

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50)
        String name,

        @NotBlank(message = "USN is required")
        @Pattern(regexp = "^[0-9][A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{3}$",
                message = "Enter a valid USN (e.g. 1AB21CS001)")
        String usn,

        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email")
        String email,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^[6-9][0-9]{9}$",
                message = "Enter a valid 10-digit Indian mobile number")
        String phone,

        @NotNull(message = "Fees is required")
        @PositiveOrZero
        BigDecimal fees,

        @NotNull
        @PositiveOrZero(message = "Pending amount cannot be negative")
        @Digits(integer = 8, fraction = 2)
        BigDecimal pending,

        @NotNull(message = "Department id is required")
        Long departmentId,

        Long roomId
) {}

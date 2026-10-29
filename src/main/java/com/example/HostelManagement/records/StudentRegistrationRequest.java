package com.example.HostelManagement.records;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record StudentRegistrationRequest(
        @NotBlank
        String name,

        @NotBlank
        String usn,
        @NotBlank
        @Email
        String email,
        @Positive
        String phone,

        @Positive
        BigDecimal fees,
        Long roomId,

        @NotBlank
        String password
) {
}

package com.example.HostelManagement.records;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record FeePaymentRequest(

        @NotBlank(message = "USN is required")
        @Pattern(regexp = "^[0-9][A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{3}$",
                message = "Enter a valid USN (e.g. 1AB21CS001)")
        String usn,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than zero")
        @Digits(integer = 8, fraction = 2, message = "Invalid amount format")
        BigDecimal amount
) {}

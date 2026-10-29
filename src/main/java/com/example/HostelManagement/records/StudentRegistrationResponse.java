package com.example.HostelManagement.records;

import com.example.HostelManagement.enums.Role;

public record StudentRegistrationResponse(
        Long studentId,
        Long userId,
        String name,
        String usn,
        String email,
        Role role
) {
}
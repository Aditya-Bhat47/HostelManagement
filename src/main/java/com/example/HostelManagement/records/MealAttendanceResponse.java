package com.example.HostelManagement.records;

import com.example.HostelManagement.enums.Status;

import java.time.LocalDateTime;

public record MealAttendanceResponse(
        Long id,
        Long mealId,
        Long studentId,
        Status status,
        LocalDateTime checkedIn
) {
}

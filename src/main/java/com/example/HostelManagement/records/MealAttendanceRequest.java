package com.example.HostelManagement.records;

import com.example.HostelManagement.entity.MessMeal;
import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public record MealAttendanceRequest(
      Long mealId,
      Long studentId,
      Status status
) {
}

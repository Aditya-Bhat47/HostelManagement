package com.example.HostelManagement.records;

import com.example.HostelManagement.enums.MessIssue;


public record MessComplaintRequest(
        String title,
        String description,
        Long studentId,
        MessIssue issueType,
        Long mealId,
        Long menuItemId
) {
}

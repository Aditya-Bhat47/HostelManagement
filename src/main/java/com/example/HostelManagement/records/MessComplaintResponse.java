package com.example.HostelManagement.records;

import com.example.HostelManagement.enums.MessIssue;
import com.example.HostelManagement.enums.Priority;
import com.example.HostelManagement.enums.Status;

public record MessComplaintResponse(
        Long id,
        String title,
        String description,
        Status status,
        Priority priority,
        boolean anonymous,
        MessIssue issueType,
        Long mealId,
        Long menuItemId
) {
}

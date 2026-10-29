package com.example.HostelManagement.records;

import com.example.HostelManagement.enums.Category;
import com.example.HostelManagement.enums.MessIssue;

public record GeneralComplaintRequest(
        String title,
        String description,
        Long studentId,
        Category category,
        Long roomId
) {
}

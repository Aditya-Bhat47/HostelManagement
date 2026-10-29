package com.example.HostelManagement.records;

import com.example.HostelManagement.enums.Category;
import com.example.HostelManagement.enums.MessIssue;
import com.example.HostelManagement.enums.Priority;
import com.example.HostelManagement.enums.Status;

public record GeneralComplaintResponse(
        Long id,
        String title,
        String description,
        Status status,
        Priority priority,
        boolean anonymous,
        Long studentId,
       Category category,
        Long roomId
) {
}

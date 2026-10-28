package com.example.HostelManagement.repository;

import com.example.HostelManagement.entity.MessComplaint;
import com.example.HostelManagement.enums.MessIssue;
import com.example.HostelManagement.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessComplaintRepo extends JpaRepository<MessComplaint,Long> {

    Page<MessComplaint> findByIssueType(MessIssue issueType, Pageable pageable);
    Page<MessComplaint> findByMealId(Long mealId,Pageable pageable);
    Page<MessComplaint> findByMenuItemId(Long menuItemId,Pageable pageable);
    Page<MessComplaint> findByStudentId(Long studentId,Pageable pageable);
    Page<MessComplaint> findByStatus(Status status,Pageable pageable);
}

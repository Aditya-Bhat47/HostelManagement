package com.example.HostelManagement.repository;

import com.example.HostelManagement.entity.GeneralComplaint;
import com.example.HostelManagement.entity.MessComplaint;
import com.example.HostelManagement.enums.Category;
import com.example.HostelManagement.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralComplaintRepo extends JpaRepository<GeneralComplaint,Long> {

    Page<GeneralComplaint> findByCategory(Category category, Pageable pageable);
    Page<GeneralComplaint> findByRoomId(Long roomId,Pageable pageable);
    Page<GeneralComplaint> findByStudentId(Long studentId,Pageable pageable);
    Page<GeneralComplaint> findByStatus(Status status, Pageable pageable);
}

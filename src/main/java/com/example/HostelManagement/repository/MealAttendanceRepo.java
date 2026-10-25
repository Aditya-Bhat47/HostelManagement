package com.example.HostelManagement.repository;

import com.example.HostelManagement.entity.MealAttendance;
import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MealAttendanceRepo extends JpaRepository<MealAttendance,Long>, JpaSpecificationExecutor<MealAttendance> {
    Page<MealAttendance> findByStudentId(Long studentId, Pageable pageable);
    Page<MealAttendance> findByMealId(Long mealId,Pageable pageable);
    Page<MealAttendance> findByStudentIdAndCheckedInAtBetween(
            Long studentId,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );

    boolean existsByStudentIdAndStatus(Long studentId, Status status);

    @Query(
            """
                select ma.student
                from MealAttendance as ma
                where ma.maelId=:mealId
                and ma.status=:status
            """
    )
    List<Student> findStudentsByMealIdAndStatus(
            @Param("mealId") Long mealID,
            @Param("ststus") Status status
    );
}

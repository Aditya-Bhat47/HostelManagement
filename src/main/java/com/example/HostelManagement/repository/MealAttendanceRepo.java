package com.example.HostelManagement.repository;

import com.example.HostelManagement.entity.MealAttendance;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MealAttendanceRepo extends JpaRepository<MealAttendance,Long>, JpaSpecificationExecutor<MealAttendance> {
}

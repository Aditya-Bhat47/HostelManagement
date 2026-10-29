package com.example.HostelManagement.repository;

import com.example.HostelManagement.entity.MessMeal;
import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.enums.MealType;
import com.example.HostelManagement.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MessMealRepo extends JpaRepository<MessMeal,Long> {

    Page<MessMeal> findByMealDate(LocalDate mealDate, Pageable pageable);
    Page<MessMeal> findByMealDateBetween(LocalDate startDate,LocalDate endDate,Pageable pageable);
    Page<MessMeal> findByMealType(MealType mealType,Pageable pageable);
    Page<MessMeal> findByMealDateAndMealType(LocalDate mealDate,MealType mealType,Pageable pageable);
    List<MessMeal> findByMealDate(LocalDate date);
    @Query("""
       SELECT ma.student
       FROM MealAttendance ma
       WHERE ma.status = :status
       """)
    List<Student> findAttendingStudents(@Param("status") Status status);
}

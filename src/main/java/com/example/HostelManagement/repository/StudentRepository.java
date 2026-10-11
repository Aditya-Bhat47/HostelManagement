package com.example.HostelManagement.repository;

import com.example.HostelManagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> findByName(String name);

    Optional<Student> findByUsn(String usn);

    boolean existsByUsn(String usn);

    boolean existsByEmail(String email);

    Long countByRoomId(Long id);

    List<Student> findByPendingGreaterThan(BigDecimal fees);
}

package com.example.HostelManagement.repository;

import com.example.HostelManagement.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByEmail(String email);
    boolean existsByEmail(String email);
}

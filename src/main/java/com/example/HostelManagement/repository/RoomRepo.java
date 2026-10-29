package com.example.HostelManagement.repository;

import com.example.HostelManagement.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepo extends JpaRepository<Rooms,Long> {
}

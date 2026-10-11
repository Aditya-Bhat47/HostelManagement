package com.example.HostelManagement.repository;

import com.example.HostelManagement.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Rooms,Long> {
}

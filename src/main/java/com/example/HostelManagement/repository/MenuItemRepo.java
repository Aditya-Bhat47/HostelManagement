package com.example.HostelManagement.repository;

import com.example.HostelManagement.entity.MenuItem;
import com.example.HostelManagement.enums.ItemCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepo extends JpaRepository<MenuItem,Long> {

    boolean existsByName(String name);
    Page<MenuItem> findByActive(boolean active, Pageable pageable);

    Page<MenuItem> findByCategory( ItemCategory category,Pageable pageable);
    Page<MenuItem> findByVegetarian(boolean vegetarian,Pageable pageable);

}

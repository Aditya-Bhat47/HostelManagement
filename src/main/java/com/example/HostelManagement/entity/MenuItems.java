package com.example.HostelManagement.entity;

import com.example.HostelManagement.enums.ItemCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean vegetarian;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemCategory category;


    @Column(nullable = false)
    private boolean active = true;

}

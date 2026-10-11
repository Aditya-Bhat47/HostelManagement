package com.example.HostelManagement.entity;

import com.example.HostelManagement.enums.MealType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessMeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate mealDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @Column(nullable = false)
    private LocalTime openTime;

    @Column(nullable = false)
    private LocalTime closeTime;


    @ManyToMany
    @JoinTable(
            name = "mess_meal_items",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<MenuItems> menuItems= new ArrayList<>();

    @OneToMany(mappedBy = "meal")
    private List<MealAttendance> attendances = new ArrayList<>();

    @Transient
    public String getDisplayItems() {
        return menuItems.stream()
                .map(MenuItems::getName)
                .collect(Collectors.joining(", "));
    }

    @Transient
    public boolean isOpenNow() {
        LocalTime now = LocalTime.now();
        return LocalDate.now().equals(mealDate)
                && !now.isBefore(openTime) && now.isBefore(closeTime);
    }


}

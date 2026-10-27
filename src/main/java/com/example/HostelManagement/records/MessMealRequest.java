package com.example.HostelManagement.records;

import com.example.HostelManagement.enums.MealType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record MessMealRequest(
        LocalDate mealDate,
        MealType mealType,
        LocalTime openTime,
        LocalTime closeTime,
        List<Long> menuItemIds


) {
}

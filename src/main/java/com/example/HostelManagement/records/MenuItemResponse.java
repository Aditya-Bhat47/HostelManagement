package com.example.HostelManagement.records;

import com.example.HostelManagement.enums.ItemCategory;

public record MenuItemResponse(
        Long id,
        String name,
        String description,
        boolean vegetarian,
        ItemCategory category,
        boolean active
) {
}

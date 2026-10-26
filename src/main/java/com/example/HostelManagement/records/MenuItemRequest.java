package com.example.HostelManagement.records;

import com.example.HostelManagement.enums.ItemCategory;

public record MenuItemRequest(
        String name,
        String description,
        boolean vegetarian,
        ItemCategory category
) {
}

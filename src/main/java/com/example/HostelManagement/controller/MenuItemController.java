package com.example.HostelManagement.controller;

import com.example.HostelManagement.enums.ItemCategory;
import com.example.HostelManagement.records.MenuItemRequest;
import com.example.HostelManagement.records.MenuItemResponse;
import com.example.HostelManagement.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menuItems")
@AllArgsConstructor
public class MenuItemController {
    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemResponse> createMenuItem(
            @RequestBody MenuItemRequest request){
        return menuItemService.createMenuItem(request);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<MenuItemResponse>> getActiveMenuItems(Pageable pageable){
        return menuItemService.getActiveMenuItems(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable Long id ){
        return menuItemService.getMenuItemById(id);
    }

    @GetMapping
    public ResponseEntity<Page<MenuItemResponse>> getAllMenuItems(Pageable pageable){
        return menuItemService.getAllMenuItems(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(
            @PathVariable Long id,
            @RequestBody MenuItemRequest request){
        return menuItemService.updateMenuItem(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delMenuItem(@PathVariable Long id){
        return menuItemService.delMenuItem(id);
    }

    @GetMapping("/category")
    public ResponseEntity<Page<MenuItemResponse>> getMenuItemsByCategory(
            Pageable pageable,
            @RequestParam("category") ItemCategory category){
        return menuItemService.getMenuItemsByCategory(pageable,category);
    }

    @GetMapping("/vegetarian")
    public ResponseEntity<Page<MenuItemResponse>> getVegetarianItems(
            @RequestParam boolean vegetarian,
            Pageable pageable) {

        return menuItemService.getVegetarianItems(vegetarian, pageable);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<String> activateMenuItem(@PathVariable Long id) {
        return menuItemService.activateMenuItem(id);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateMenuItem(@PathVariable Long id) {
        return menuItemService.deactivateMenuItem(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Boolean> searchMenuItemsByName(
            @RequestParam("name") String name) {

        return menuItemService.searchMenuItemsByName(name);
    }
}

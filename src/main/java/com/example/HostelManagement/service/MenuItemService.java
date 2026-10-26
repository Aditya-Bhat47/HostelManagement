package com.example.HostelManagement.service;

import com.example.HostelManagement.entity.MenuItem;
import com.example.HostelManagement.enums.ItemCategory;
import com.example.HostelManagement.exceptionHandler.DuplicateResourceException;
import com.example.HostelManagement.exceptionHandler.ResourceNotFoundexception;
import com.example.HostelManagement.records.MenuItemRequest;
import com.example.HostelManagement.records.MenuItemResponse;
import com.example.HostelManagement.repository.MenuItemRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@AllArgsConstructor
public class MenuItemService {
    private final MenuItemRepo menuItemRepo;

    public ResponseEntity<MenuItemResponse> createMenuItem(MenuItemRequest request){
        if (menuItemRepo.existsByName(request.name())){
            throw new  DuplicateResourceException("The menu item is already saved");
        }
        MenuItem menuItem=toMenuItem(request);
        menuItemRepo.save(menuItem);
        return ResponseEntity.ok(toMenuItemResponse(menuItem));
    }

    public ResponseEntity<MenuItemResponse> getMenuItemById(Long id ){
        MenuItem menuItem=menuItemRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("Menu item Not found"));
        return ResponseEntity.ok(toMenuItemResponse(menuItem));
    }

    public ResponseEntity<Page<MenuItemResponse>> getAllMenuItems(Pageable pageable){
        Page<MenuItem> menu=menuItemRepo.findAll(pageable);
        Page<MenuItemResponse> responses=menu.map(this::toMenuItemResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<MenuItemResponse> updateMenuItem(Long id,MenuItemRequest request){
        MenuItem menuItem=menuItemRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("Menu Item ot found"));
        menuItem.setName(request.name());
        menuItem.setCategory(request.category());
        menuItem.setVegetarian(request.vegetarian());
        menuItem.setDescription(request.description());
        menuItemRepo.save(menuItem);
        return ResponseEntity.ok(toMenuItemResponse(menuItem));
    }

    public ResponseEntity<String> delMenuItem(Long id){
        MenuItem menuItem=menuItemRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("Menu Item not found"));
        menuItemRepo.delete(menuItem);
        return ResponseEntity.ok("Menu Item Deleted Successfully");
    }

    public ResponseEntity<Page<MenuItemResponse>> getActiveMenuItems(Pageable pageable){
        Page<MenuItem> menuItem=menuItemRepo.findByActive(true,pageable);
        Page<MenuItemResponse> responses=menuItem.map(this::toMenuItemResponse);
        return ResponseEntity.ok(responses);

    }

    public ResponseEntity<Page<MenuItemResponse>> getMenuItemsByCategory(Pageable pageable,
                                                                         ItemCategory category){

        Page<MenuItem> menuItems=menuItemRepo.findByCategory(category,pageable);
        Page<MenuItemResponse> responses=menuItems.map(this::toMenuItemResponse);
        return ResponseEntity.ok(responses);

    }

    public ResponseEntity<Page<MenuItemResponse>> getVegetarianItems(boolean vegetarian,Pageable pageable){
        Page<MenuItem> menuItems=menuItemRepo.findByVegetarian(vegetarian,pageable);
        Page<MenuItemResponse> responses=menuItems.map(this::toMenuItemResponse);
        return ResponseEntity.ok(responses);
    }

    //admin related operations

    public ResponseEntity<String> activateMenuItem(Long id){
        MenuItem menuItem=menuItemRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("resource not found"));
        menuItem.setActive(true);
        menuItemRepo.save(menuItem);
        return ResponseEntity.ok("Menu Item activated successfully");
    }

    public ResponseEntity<String> deactivateMenuItem(Long id) {

        MenuItem menuItem = menuItemRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundexception("Menu Item not found"));

        menuItem.setActive(false);
        menuItemRepo.save(menuItem);

        return ResponseEntity.ok("Menu Item deactivated successfully");
    }

    public ResponseEntity<Boolean> searchMenuItemsByName(String name){
        boolean menuItem=menuItemRepo.existsByName(name);
        return ResponseEntity.ok(menuItem);
    }

    public MenuItem toMenuItem(MenuItemRequest request){
        MenuItem menuItem=new MenuItem();
        menuItem.setName(request.name());
        menuItem.setCategory(request.category());
        menuItem.setVegetarian(request.vegetarian());
        menuItem.setDescription(request.description());
        return menuItem;
    }
    public MenuItemResponse toMenuItemResponse(MenuItem menuItem){
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.isVegetarian(),
                menuItem.getCategory(),
                menuItem.isActive()
        );
    }
}

package com.example.HostelManagement.controller;

import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.enums.MealType;
import com.example.HostelManagement.records.MealAttendanceResponse;
import com.example.HostelManagement.records.MenuItemResponse;
import com.example.HostelManagement.records.MessMealRequest;
import com.example.HostelManagement.records.MessMealResponse;
import com.example.HostelManagement.service.MessMealService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/messmeal")
public class MessMealController {
    private final MessMealService messMealService;

    @PostMapping
    public ResponseEntity<MessMealResponse> createMeal(
            @RequestBody MessMealRequest request
            ){
        return messMealService.createMeal(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessMealResponse> getMealById(@PathVariable Long id){
        return messMealService.getMealById(id);
    }

    @GetMapping
    public ResponseEntity<Page<MessMealResponse>> getAllMeals(Pageable pageable){
        return messMealService.getAllMeals(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessMealResponse> updateMeal(@PathVariable Long id,
                                                       @RequestBody MessMealRequest request){
        return messMealService.updateMeal(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delMeal(@PathVariable Long id){
        return messMealService.delMeal(id);
    }

    @GetMapping("/meals/dates")
    public ResponseEntity<Page<MessMealResponse>> getMealsByDate(@RequestParam LocalDate mealDate,
                                                                 Pageable pageable){
        return messMealService.getMealsByDate(mealDate,pageable);
    }

    @GetMapping("/meals/dates")
    public ResponseEntity<Page<MessMealResponse>> getMealsBetweenDates(@RequestParam LocalDate startDate,
                                                                       @RequestParam LocalDate endDate,
                                                                       Pageable pageable){
        return messMealService.getMealsBetweenDates(startDate,endDate,pageable);
    }

    @GetMapping("/todayMeal")
    public ResponseEntity<Page<MessMealResponse>> getTodayMeals(Pageable pageable){
        return messMealService.getTodayMeals(pageable);
    }

    @GetMapping("/mealType")
    public ResponseEntity<Page<MessMealResponse>> getMealsByType(@RequestParam MealType mealType,
                                                                 Pageable pageable){
        return messMealService.getMealsByType(mealType,pageable);
    }

    @GetMapping("/mealDate/mealType")
    public ResponseEntity<Page<MessMealResponse>> getMealForDateAndType(@RequestParam LocalDate mealDate,
                                                                        @RequestParam MealType mealType,
                                                                        Pageable pageable){
        return messMealService.getMealForDateAndType(mealDate,mealType,pageable);
    }

    @PostMapping("/{mealId}/menu-items-add/{menuItemId}")
    public ResponseEntity<String> addMenuItemToMeal(
            @PathVariable Long mealId,
            @PathVariable Long menuItemId) {

        return messMealService.addMenuItemToMeal(mealId, menuItemId);
    }

    @PatchMapping("/{mealId}/menu-items-remove/{menuItemId}")
    public ResponseEntity<String> removeMenuItemToMeal(
            @PathVariable Long mealId,
            @PathVariable Long menuItemId) {

        return messMealService.removeMenuItemFromMeal(mealId, menuItemId);
    }

    @GetMapping("/{mealId}/menu-items")
    public ResponseEntity<List<MenuItemResponse>> getMenuItemsForMeal(
            @PathVariable Long mealId) {

        return messMealService.getMenuItemsForMeal(mealId);
    }

    @GetMapping("/currently-open")
    public ResponseEntity<List<MessMealResponse>> getCurrentlyOpenMeals() {
        return messMealService.getCurrentlyOpenMeals();
    }

    @GetMapping("/is-open")
    public ResponseEntity<Boolean> isMealCurrentlyOpen() {
        return messMealService.isMealCurrentlyOpen();
    }

    @GetMapping("/{mealId}/attendance/{attendanceId}")
    public ResponseEntity<MealAttendanceResponse> getAttendanceForMeal(
            @PathVariable Long mealId,
            @PathVariable Long attendanceId) {

        return messMealService.getAttendanceForMeal(
                mealId,
                attendanceId
        );
    }
    @GetMapping("/count")
    public ResponseEntity<Long> getAttendanceCount() {
        return messMealService.getAttendanceCount();
    }

    @GetMapping("/attending-students")
    public ResponseEntity<List<Student>> getAttendingStudents() {
        return messMealService.getAttendingStudents();
    }
}

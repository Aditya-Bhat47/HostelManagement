package com.example.HostelManagement.controller;

import com.example.HostelManagement.entity.MessMeal;
import com.example.HostelManagement.records.MealAttendanceRequest;
import com.example.HostelManagement.records.MealAttendanceResponse;
import com.example.HostelManagement.service.MealAttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/mealAttendance")
public class MealAttendanceController {

    private final MealAttendanceService mealAttendanceService;

    @PostMapping()
    public ResponseEntity<MealAttendanceResponse> createMealAttendance(
            @RequestBody MealAttendanceRequest request){
        return mealAttendanceService.createAttendance(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealAttendanceResponse> getAttendanceById(
            @PathVariable Long id
    ){
        return mealAttendanceService.getAttendanceById(id);
    }

    @GetMapping
    public ResponseEntity<Page<MealAttendanceResponse>> getAllAttendance(Pageable pageable){
        return mealAttendanceService.getAllAttendance(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealAttendanceResponse> updateAttendance(@PathVariable Long id,
                                                                   @RequestBody MealAttendanceRequest request){
        return mealAttendanceService.updateAttendance(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delAttendance(@PathVariable Long id){
        return mealAttendanceService.delAttendance(id);
    }


}

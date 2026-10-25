package com.example.HostelManagement.controller;

import com.example.HostelManagement.entity.MessMeal;
import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.enums.Status;
import com.example.HostelManagement.records.MealAttendanceRequest;
import com.example.HostelManagement.records.MealAttendanceResponse;
import com.example.HostelManagement.service.MealAttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @PatchMapping("/{id}/mark")
    public ResponseEntity<String> markMealAttendance(@PathVariable Long id){
        return mealAttendanceService.markMealAttendance(id);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateAttendanceStatus(@PathVariable Long id, @RequestParam Status status){
        return mealAttendanceService.updateAttendanceStatus(id,status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<MealAttendanceResponse>> getAttendanceByStudent(@PathVariable Long id,Pageable pageable){
        return mealAttendanceService.getAttendanceByStudent(id,pageable);
    }

    @GetMapping("/meal/{id}")
    public ResponseEntity<Page<MealAttendanceResponse>> getAttendanceByMeal(@PathVariable Long id,Pageable pageable){
        return mealAttendanceService.getAttendanceByMeal(id,pageable);
    }

    @GetMapping("/student/{id}/localDateTime")
    public ResponseEntity<Page<MealAttendanceResponse>>
    getAttendanceForStudentOnDate(@PathVariable Long id,
                                  @RequestParam LocalDateTime localDateTime,
                                  Pageable pageable
    ){
        return mealAttendanceService.getAttendanceForStudentOnDate(id,localDateTime,pageable);
    }


    @GetMapping("/student/{id}/attended")
    public ResponseEntity<Boolean> hasStudentAttendedMeal(@PathVariable Long id){
        return mealAttendanceService.hasStudentAttendedMeal(id);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getMealAttendanceCount(){
        return mealAttendanceService.getMealAttendanceCount();
    }

    @GetMapping("/meal/{mealId}/students")
    public ResponseEntity<List<Student>> getStudentsAttendedMeal(@PathVariable Long mealId){
        return mealAttendanceService.getStudentsAttendedMeal(mealId);
    }
}

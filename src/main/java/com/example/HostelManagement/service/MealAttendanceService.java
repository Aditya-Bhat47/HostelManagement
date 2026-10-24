package com.example.HostelManagement.service;

import com.example.HostelManagement.entity.MealAttendance;
import com.example.HostelManagement.entity.MessMeal;
import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.exceptionHandler.ResourceNotFoundexception;
import com.example.HostelManagement.records.MealAttendanceRequest;
import com.example.HostelManagement.records.MealAttendanceResponse;
import com.example.HostelManagement.repository.MealAttendanceRepo;
import com.example.HostelManagement.repository.MessMealRepo;
import com.example.HostelManagement.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class MealAttendanceService {

    private final MealAttendanceRepo mealAttendanceRepo;
    private final MessMealRepo messMealRepo;
    private final StudentRepository studentRepository;

    public ResponseEntity<MealAttendanceResponse> createAttendance(MealAttendanceRequest request){
        MessMeal meal=messMealRepo.findById(request.mealId())
                .orElseThrow(()->new ResourceNotFoundexception("meal not found"));

        Student student=studentRepository.findById(request.studentId())
                .orElseThrow(()->new ResourceNotFoundexception("Student not found"));

        MealAttendance mealAttendance=toMealAttendance(request,meal,student);
        mealAttendanceRepo.save(mealAttendance);
        return ResponseEntity.ok(toMealAttendanceResponse(mealAttendance));

    }

    public ResponseEntity<MealAttendanceResponse> getAttendanceById(Long id){
        MealAttendance mealAttendance=mealAttendanceRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("Attendance Not found"));
        return ResponseEntity.ok(toMealAttendanceResponse(mealAttendance));
    }

    public ResponseEntity<Page<MealAttendanceResponse>> getAllAttendance(Pageable pageable){
        Page<MealAttendance> mealAttendance=mealAttendanceRepo.findAll(pageable);
       Page<MealAttendanceResponse> responses=mealAttendance.map(this::toMealAttendanceResponse);
       return ResponseEntity.ok(responses);
    }

    public ResponseEntity<MealAttendanceResponse> updateAttendance(Long id,MealAttendanceRequest request){
        MealAttendance update=mealAttendanceRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("Attendance not found"));
        MessMeal meal=messMealRepo.findById(request.mealId())
                .orElseThrow(()->new ResourceNotFoundexception("Meal Not found"));
        Student student=studentRepository.findById(request.studentId())
                .orElseThrow(()->new ResourceNotFoundexception("Student not found"));


        update.setStatus(request.status());
        update.setMeal(meal);
        update.setStudent(student);
        mealAttendanceRepo.save(update);
        return ResponseEntity.ok(toMealAttendanceResponse(update));
    }
    public ResponseEntity<String> delAttendance(Long id){
        MealAttendance mealAttendance=mealAttendanceRepo.findById(id)
                        .orElseThrow(()->new ResourceNotFoundexception("Attendance not Found "));
        mealAttendanceRepo.delete(mealAttendance);
        return ResponseEntity.ok("record deleted successfully");

    }
    public MealAttendance toMealAttendance(MealAttendanceRequest request,
                                           MessMeal meal,
                                           Student student){

        MealAttendance mealAttendance=new MealAttendance();
        mealAttendance.setMeal(meal);
        mealAttendance.setStudent(student);
        mealAttendance.setStatus(request.status());
        return mealAttendance;
    }

    public MealAttendanceResponse toMealAttendanceResponse(MealAttendance mealAttendance){
        return new MealAttendanceResponse(
                mealAttendance.getId(),
                mealAttendance.getMeal().getId(),
                mealAttendance.getStudent().getId(),
                mealAttendance.getStatus(),
                mealAttendance.getCheckedInAt()
        );
    }


}

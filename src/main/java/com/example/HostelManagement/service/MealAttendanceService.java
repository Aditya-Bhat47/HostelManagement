package com.example.HostelManagement.service;

import com.example.HostelManagement.entity.MealAttendance;
import com.example.HostelManagement.entity.MessMeal;
import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.enums.Status;
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

import java.time.LocalDateTime;
import java.util.List;

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

    public ResponseEntity<String> markMealAttendance(Long id){
        MealAttendance mealAttendance=mealAttendanceRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("attendance not found"));
        mealAttendance.setStatus(Status.ATTENDED);
        mealAttendanceRepo.save(mealAttendance);
        return ResponseEntity.ok("Attendance Marked");
    }

    public ResponseEntity<String> updateAttendanceStatus(Long id,Status status){
        MealAttendance mealAttendance=mealAttendanceRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundexception("Attendane not found "));
        mealAttendance.setStatus(status);
        mealAttendanceRepo.save(mealAttendance);
        return ResponseEntity.ok("Updated attendance");
    }

    public ResponseEntity<Page<MealAttendanceResponse>> getAttendanceByStudent(Long id,Pageable pageable){
        Page<MealAttendance> mealAttendances=mealAttendanceRepo.findByStudentId(id,pageable);
        Page<MealAttendanceResponse> responses=mealAttendances.map(this::toMealAttendanceResponse);
        return ResponseEntity.ok(responses);

    }

    public ResponseEntity<Page<MealAttendanceResponse>> getAttendanceByMeal(Long id,Pageable pageable){
        Page<MealAttendance> mealAttendances=mealAttendanceRepo.findByMealId(id,pageable);
        Page<MealAttendanceResponse> responses=mealAttendances.map(this::toMealAttendanceResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<Page<MealAttendanceResponse>>
    getAttendanceForStudentOnDate(Long id,
                                  LocalDateTime localDateTime,
                                  Pageable pageable
    ){
      LocalDateTime start=localDateTime.toLocalDate().atStartOfDay();
      LocalDateTime end=start.plusDays(1).minusNanos(1);

        Page<MealAttendance> mealAttendances =
                mealAttendanceRepo.findByStudentIdAndCheckedInAtBetween(
                        id,
                        start,
                        end,
                        pageable
                );

        Page<MealAttendanceResponse> responses =
                mealAttendances.map(this::toMealAttendanceResponse);

        return ResponseEntity.ok(responses);
    }
    public ResponseEntity<Boolean> hasStudentAttendedMeal(Long id){
        boolean attended=mealAttendanceRepo.existsByStudentIdAndStatus(id,Status.ATTENDED);

        return ResponseEntity.ok(attended);
    }
    
    public ResponseEntity<Long> getMealAttendanceCount(){
        Long count=mealAttendanceRepo.count();
        return ResponseEntity.ok(count);
    }

    public ResponseEntity<List<Student>> getStudentsAttendedMeal(Long mealId){
        List<Student> students=mealAttendanceRepo.findStudentsByMealIdAndStatus(mealId,Status.ATTENDED);
        return ResponseEntity.ok(students);
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

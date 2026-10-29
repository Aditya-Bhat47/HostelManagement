package com.example.HostelManagement.service;

import com.example.HostelManagement.entity.MenuItem;
import com.example.HostelManagement.entity.MessComplaint;
import com.example.HostelManagement.entity.MessMeal;
import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.enums.MessIssue;
import com.example.HostelManagement.enums.Status;
import com.example.HostelManagement.exceptionHandler.ResourceNotFoundexception;
import com.example.HostelManagement.records.MessComplaintRequest;
import com.example.HostelManagement.records.MessComplaintResponse;
import com.example.HostelManagement.repository.MenuItemRepo;
import com.example.HostelManagement.repository.MessComplaintRepo;
import com.example.HostelManagement.repository.MessMealRepo;
import com.example.HostelManagement.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessComplaintService {
    private final MessComplaintRepo messComplaintRepo;
    private final MenuItemRepo menuItemRepo;
    private final MessMealRepo messMealRepo;
    private final StudentRepository studentRepository;

    public ResponseEntity<MessComplaintResponse> createMessComplaint(MessComplaintRequest request){
        MessMeal meal = messMealRepo.findById(request.mealId())
                .orElseThrow(() ->
                        new ResourceNotFoundexception("Meal not found"));

        MenuItem menuItem = menuItemRepo.findById(request.menuItemId())
                .orElseThrow(() ->
                        new ResourceNotFoundexception("Menu item not found"));

        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() ->
                        new ResourceNotFoundexception("Student not found"));

        MessComplaint messComplaint = new MessComplaint();

        messComplaint.setTitle(request.title());
        messComplaint.setDescription(request.description());
        messComplaint.setStudent(student);
        messComplaint.setIssueType(request.issueType());
        messComplaint.setMeal(meal);
        messComplaint.setMenuItem(menuItem);
        messComplaintRepo.save(messComplaint);
        return ResponseEntity.ok(toMessComplaintResponse(messComplaint));
    }

    public ResponseEntity<MessComplaintResponse> getMessComplaintById(Long id){
        MessComplaint messComplaint=messComplaintRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("Mess complaint not found"));
        return ResponseEntity.ok(toMessComplaintResponse(messComplaint));
    }

    public ResponseEntity<Page<MessComplaintResponse>> getAllMessComplaints(Pageable pageable){
        Page<MessComplaint> messComplaints=messComplaintRepo.findAll(pageable);
        Page<MessComplaintResponse> responses=messComplaints.map(this::toMessComplaintResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<MessComplaintResponse> updateMessComplaint(Long id,MessComplaintRequest request){
        MessComplaint messComplaint=messComplaintRepo.findById(id).orElseThrow(()->new ResourceNotFoundexception("Mess complaint not found"));
        MessMeal meal=messMealRepo.findById(request.mealId()).
                orElseThrow(()->new ResourceNotFoundexception("Meal not found"));
        MenuItem menuItem=menuItemRepo.findById(request.menuItemId()).
                orElseThrow(()->new ResourceNotFoundexception("Menu item not found"));
        messComplaint.setDescription(request.description());
        messComplaint.setTitle(request.title());
        messComplaint.setStudent(messComplaint.getStudent());
        messComplaint.setMeal(meal);
        messComplaint.setMenuItem(menuItem);
        messComplaint.setIssueType(request.issueType());
        MessComplaint saved=messComplaintRepo.save(messComplaint);
        return ResponseEntity.ok(toMessComplaintResponse(saved));
    }

    public ResponseEntity<String > delMessComplaint(Long id){
        MessComplaint messComplaint=messComplaintRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("Complaint not found"));
        messComplaintRepo.delete(messComplaint);
        return ResponseEntity.ok("record deleted successfully");

    }

    public ResponseEntity<Page<MessComplaintResponse>> getComplaintsByIssueType(MessIssue issueType,
                                                                                Pageable pageable){
        Page<MessComplaint> messComplaints=messComplaintRepo.findByIssueType(issueType,pageable);
        Page<MessComplaintResponse> responses=messComplaints.map(this::toMessComplaintResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<Page<MessComplaintResponse>> getComplaintsForMeal(Long mealId,Pageable pageable){
        MessMeal meal=messMealRepo.findById(mealId)
                .orElseThrow(()->new ResourceNotFoundexception("Meal not found"));
        Page<MessComplaint> complaints =
                messComplaintRepo.findByMealId(mealId, pageable);
        Page<MessComplaintResponse> responses=complaints.map(this::toMessComplaintResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<Page<MessComplaintResponse>> getComplaintsForMenuItem(Long menuItemId,Pageable pageable){
        MenuItem menuItem=menuItemRepo.findById(menuItemId)
                .orElseThrow(()->new ResourceNotFoundexception("Menu Item Not found"));
        Page<MessComplaint> complaints=messComplaintRepo.findByMenuItemId(menuItemId,pageable);
        Page<MessComplaintResponse> responses=complaints.map(this::toMessComplaintResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<Page<MessComplaintResponse>> getComplaintsByStudent(Long studentId,Pageable pageable){
        Student student=studentRepository.findById(studentId)
                .orElseThrow(()->new ResourceNotFoundexception("Student not found"));
        Page<MessComplaint> complaints=messComplaintRepo.findByStudentId(studentId,pageable);
        Page<MessComplaintResponse> responses=complaints.map(this::toMessComplaintResponse);
        return ResponseEntity.ok(responses);

    }

    //Admin/business operations

    public ResponseEntity<String> resolveComplaint(Long messComplaintId,String note){
        MessComplaint messComplaint=messComplaintRepo.findById(messComplaintId)
                .orElseThrow(()->new ResourceNotFoundexception("Mess complaint not found"));
        messComplaint.resolve(note);
            messComplaintRepo.save(messComplaint);
        return ResponseEntity.ok("Mess complaint resolved");
    }

    public ResponseEntity<String> rejectComplaint(
            Long complaintId,
            String note) {

        MessComplaint messComplaint=messComplaintRepo.findById(complaintId)
                .orElseThrow(()->new ResourceNotFoundexception("Mess complaint not found"));
        messComplaint.reject(note);
        messComplaintRepo.save(messComplaint);
        return ResponseEntity.ok("Mess complaint rejected");
    }

    public ResponseEntity<Page<MessComplaintResponse>> getPendingComplaints( Pageable pageable){
        Page<MessComplaint> messComplaints=messComplaintRepo.findByStatus(Status.OPEN,pageable);
        Page<MessComplaintResponse> responses=messComplaints.map(this::toMessComplaintResponse);
        return ResponseEntity.ok(responses);
    }
    public ResponseEntity<Page<MessComplaintResponse>> getResolvedComplaints(Pageable pageable){
        Page<MessComplaint> messComplaints=messComplaintRepo.findByStatus(Status.RESOLVED,pageable);
        Page<MessComplaintResponse> responses=messComplaints.map(this::toMessComplaintResponse);
        return ResponseEntity.ok(responses);
    }


    public MessComplaint toMessComplaint(MessComplaintRequest request) {

        MessMeal meal = messMealRepo.findById(request.mealId())
                .orElseThrow(() ->
                        new ResourceNotFoundexception("Meal not found"));

        MenuItem menuItem = menuItemRepo.findById(request.menuItemId())
                .orElseThrow(() ->
                        new ResourceNotFoundexception("Menu item not found"));

        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() ->
                        new ResourceNotFoundexception("Student not found"));

        MessComplaint messComplaint = new MessComplaint();

        messComplaint.setTitle(request.title());
        messComplaint.setDescription(request.description());
        messComplaint.setStudent(student);
        messComplaint.setIssueType(request.issueType());
        messComplaint.setMeal(meal);
        messComplaint.setMenuItem(menuItem);

        return messComplaint;
    }

    public MessComplaintResponse toMessComplaintResponse(MessComplaint messComplaint){
        return new MessComplaintResponse(
                messComplaint.getId(),
                messComplaint.getTitle(),
                messComplaint.getDescription(),
                messComplaint.getStatus(),
                messComplaint.getPriority(),
                messComplaint.isAnonymous(),
                messComplaint.getIssueType(),
                messComplaint.getMeal().getId(),
                messComplaint.getMenuItem().getId()

        );
    }
}

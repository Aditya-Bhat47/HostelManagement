package com.example.HostelManagement.service;

import com.example.HostelManagement.entity.GeneralComplaint;
import com.example.HostelManagement.entity.MessComplaint;
import com.example.HostelManagement.entity.Rooms;
import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.enums.Category;
import com.example.HostelManagement.enums.Status;
import com.example.HostelManagement.exceptionHandler.ResourceNotFoundexception;
import com.example.HostelManagement.records.GeneralComplaintRequest;
import com.example.HostelManagement.records.GeneralComplaintResponse;
import com.example.HostelManagement.repository.GeneralComplaintRepo;
import com.example.HostelManagement.repository.RoomRepo;
import com.example.HostelManagement.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeneralComplaintService {
    private final GeneralComplaintRepo generalComplaintRepo;
    private final RoomRepo roomRepo;

    private final StudentRepository studentRepository;

    public ResponseEntity<GeneralComplaintResponse> createGeneralComplaint(GeneralComplaintRequest request){
       GeneralComplaint generalComplaint=toGeneralComplaint(request);
       GeneralComplaint saved=generalComplaintRepo.save(generalComplaint);
        return ResponseEntity.ok(toGeneralComplaintResponse(saved));
    }

    public ResponseEntity<GeneralComplaintResponse> getGeneralComplaintById(Long id){
        GeneralComplaint generalComplaint=generalComplaintRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("Resource not found"));
        return ResponseEntity.ok(toGeneralComplaintResponse(generalComplaint));
    }

    public ResponseEntity<Page<GeneralComplaintResponse>> getAllGeneralComplaints(Pageable pageable){
        Page<GeneralComplaint> generalComplaints=generalComplaintRepo.findAll(pageable);
        Page<GeneralComplaintResponse> responses=generalComplaints.map(this::toGeneralComplaintResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<GeneralComplaintResponse> resolveComplaint(
            Long id, String note) {

        GeneralComplaint complaint = generalComplaintRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundexception("Complaint not found"));

        complaint.resolve(note);

        GeneralComplaint saved = generalComplaintRepo.save(complaint);

        return ResponseEntity.ok(toGeneralComplaintResponse(saved));
    }

    public ResponseEntity<GeneralComplaintResponse> rejectComplaint(
            Long id, String note) {

        GeneralComplaint complaint = generalComplaintRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundexception("Complaint not found"));

        complaint.reject(note);

        GeneralComplaint saved = generalComplaintRepo.save(complaint);

        return ResponseEntity.ok(toGeneralComplaintResponse(saved));
    }
    public ResponseEntity<Page<GeneralComplaintResponse>> getPendingComplaints(
            Pageable pageable) {

        Page<GeneralComplaint> complaints =
                generalComplaintRepo.findByStatus(Status.OPEN, pageable);

        Page<GeneralComplaintResponse> responses =
                complaints.map(this::toGeneralComplaintResponse);

        return ResponseEntity.ok(responses);
    }
    public ResponseEntity<Page<GeneralComplaintResponse>> getResolvedComplaints(
            Pageable pageable) {

        Page<GeneralComplaint> complaints =
                generalComplaintRepo.findByStatus(Status.RESOLVED, pageable);

        Page<GeneralComplaintResponse> responses =
                complaints.map(this::toGeneralComplaintResponse);

        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<GeneralComplaintResponse> updateGeneralComplaint(GeneralComplaintRequest request,Long id){
        GeneralComplaint generalComplaint=generalComplaintRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("complaint not found"));
        Student student=studentRepository.findById(request.studentId())
                .orElseThrow(()->new ResourceNotFoundexception("student not found"));
        Rooms rooms=roomRepo.findById(request.roomId())
                .orElseThrow(()->new ResourceNotFoundexception("Room not found"));


        generalComplaint.setDescription(request.description());
        generalComplaint.setTitle(request.title());
        generalComplaint.setStudent(student);
        generalComplaint.setCategory(request.category());
        generalComplaint.setRoom(rooms);
        GeneralComplaint saved=generalComplaintRepo.save(generalComplaint);
        return ResponseEntity.ok(toGeneralComplaintResponse(saved));
    }

    public ResponseEntity<String> deleteGeneralComplaint(Long id){
        GeneralComplaint generalComplaint=generalComplaintRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundexception("complaint not found"));
        generalComplaintRepo.delete(generalComplaint);
        return ResponseEntity.ok("record deleted successfully");
    }

    public ResponseEntity<Page<GeneralComplaintResponse>> getComplaintsByCategory(Category category,Pageable pageable){
        Page<GeneralComplaint> generalComplaints=generalComplaintRepo.findByCategory(category,pageable);
        Page<GeneralComplaintResponse> responses=generalComplaints.map(this::toGeneralComplaintResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<Page<GeneralComplaintResponse>> getComplaintsForRoom(Long roomId,Pageable pageable){
        Page<GeneralComplaint> generalComplaints=generalComplaintRepo.findByRoomId(roomId,pageable);
        Page<GeneralComplaintResponse> responses=generalComplaints.map(this::toGeneralComplaintResponse);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<Page<GeneralComplaintResponse>> getComplaintsByStudent(Long studentId,Pageable pageable){
        Page<GeneralComplaint> generalComplaints=generalComplaintRepo.findByStudentId(studentId,pageable);
        Page<GeneralComplaintResponse> responses=generalComplaints.map(this::toGeneralComplaintResponse);
        return ResponseEntity.ok(responses);
    }


    public GeneralComplaint toGeneralComplaint(GeneralComplaintRequest request){
            Student student=studentRepository.findById(request.studentId())
                    .orElseThrow(()->new ResourceNotFoundexception("student not found"));
            Rooms rooms=roomRepo.findById(request.roomId())
                    .orElseThrow(()->new ResourceNotFoundexception("Room not found"));

            GeneralComplaint generalComplaint=new GeneralComplaint();
            generalComplaint.setDescription(request.description());
            generalComplaint.setTitle(request.title());
            generalComplaint.setStudent(student);
            generalComplaint.setCategory(request.category());
            generalComplaint.setRoom(rooms);
            return generalComplaint;
    }

    public GeneralComplaintResponse toGeneralComplaintResponse(GeneralComplaint generalComplaint){
        return new GeneralComplaintResponse(
                generalComplaint.getId(),
                generalComplaint.getTitle(),
                generalComplaint.getDescription(),
                generalComplaint.getStatus(),
                generalComplaint.getPriority(),
                generalComplaint.isAnonymous(),
                generalComplaint.getStudent().getId(),
                generalComplaint.getCategory(),
                generalComplaint.getRoom().getId()
        );
    }


}

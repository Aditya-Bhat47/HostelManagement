package com.example.HostelManagement.controller;

import com.example.HostelManagement.enums.MessIssue;
import com.example.HostelManagement.records.MessComplaintRequest;
import com.example.HostelManagement.records.MessComplaintResponse;
import com.example.HostelManagement.service.MessComplaintService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/messcomplaint")
public class MessComplaintController {
    private final MessComplaintService messComplaintService;

    @PostMapping
    public ResponseEntity<MessComplaintResponse> createMessComplaint(@RequestBody MessComplaintRequest request){
        return messComplaintService.createMessComplaint(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessComplaintResponse> getMessComplaintById(@PathVariable Long id){
        return messComplaintService.getMessComplaintById(id);
    }

    @GetMapping
    public ResponseEntity<Page<MessComplaintResponse>> getAllMessComplaints(Pageable pageable){
        return messComplaintService.getAllMessComplaints(pageable);
    }

    @PutMapping
    public ResponseEntity<MessComplaintResponse> updateMessComplaint(@PathVariable Long id,@RequestBody MessComplaintRequest request){
        return messComplaintService.updateMessComplaint(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String > delMessComplaint(@PathVariable Long id){
        return messComplaintService.delMessComplaint(id);
    }

    @GetMapping("/issue-type")
    public ResponseEntity<Page<MessComplaintResponse>> getComplaintsByIssueType(@RequestParam MessIssue issueType,
                                                                                Pageable pageable){
        return messComplaintService.getComplaintsByIssueType(issueType, pageable);
    }

    @GetMapping("/meal-complaint/{mealId}")
    public ResponseEntity<Page<MessComplaintResponse>> getComplaintsForMeal(@PathVariable Long mealId,Pageable pageable){
        return messComplaintService.getComplaintsForMeal(mealId,pageable);
    }

    @GetMapping("/menu-item-complaint/{menuItemId}")
    public ResponseEntity<Page<MessComplaintResponse>> getComplaintsForMenuItem(@PathVariable Long menuItemId,Pageable pageable){
        return messComplaintService.getComplaintsForMenuItem(menuItemId,pageable);
    }

    @GetMapping("/student-complaints/{studentId}")
    public ResponseEntity<Page<MessComplaintResponse>> getComplaintsByStudent(@PathVariable Long studentId,Pageable pageable){
        return messComplaintService.getComplaintsByStudent(studentId,pageable);
    }

    @PatchMapping("/{messComplaintId}/resolve")
    public ResponseEntity<String> resolveComplaint(
            @PathVariable Long messComplaintId,
            @RequestParam String note) {

        return messComplaintService.resolveComplaint(
                messComplaintId,
                note
        );
    }

    @PatchMapping("/{complaintId}/reject")
    public ResponseEntity<String> rejectComplaint(
            @PathVariable Long complaintId,
            @RequestParam String note) {

        return messComplaintService.rejectComplaint(complaintId, note);
    }
    @GetMapping("/pending")
    public ResponseEntity<Page<MessComplaintResponse>> getPendingComplaints(
            Pageable pageable) {

        return messComplaintService.getPendingComplaints(pageable);
    }

    @GetMapping("/resolved")
    public ResponseEntity<Page<MessComplaintResponse>> getResolvedComplaints(
            Pageable pageable) {

        return messComplaintService.getResolvedComplaints(pageable);
    }
}

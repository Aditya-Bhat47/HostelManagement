package com.example.HostelManagement.controller;

import com.example.HostelManagement.enums.Category;
import com.example.HostelManagement.records.GeneralComplaintRequest;
import com.example.HostelManagement.records.GeneralComplaintResponse;
import com.example.HostelManagement.service.GeneralComplaintService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/generalComplaints")
public class GeneralComplaintController {
    private final GeneralComplaintService generalComplaintService;

    @PostMapping
    public ResponseEntity<GeneralComplaintResponse> createGeneralComplaint(
            @RequestBody GeneralComplaintRequest request){
        return  generalComplaintService.createGeneralComplaint(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralComplaintResponse> getGeneralComplaintById(@PathVariable Long id){
        return generalComplaintService.getGeneralComplaintById(id);
    }

    @GetMapping
    public ResponseEntity<Page<GeneralComplaintResponse>> getAllGeneralComplaints(Pageable pageable){
        return generalComplaintService.getAllGeneralComplaints(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralComplaintResponse> updateGeneralComplaint(
            @RequestBody GeneralComplaintRequest request,@PathVariable Long id){
        return generalComplaintService.updateGeneralComplaint(request,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGeneralComplaint(@PathVariable Long id){
        return generalComplaintService.deleteGeneralComplaint(id);
    }

    @GetMapping("/category")
    public ResponseEntity<Page<GeneralComplaintResponse>> getComplaintsByCategory(
            @RequestParam Category category, Pageable pageable){
        return generalComplaintService.getComplaintsByCategory(category,pageable);
    }

    @GetMapping("/room")
    public ResponseEntity<Page<GeneralComplaintResponse>> getComplaintsForRoom(
            @RequestParam Long roomId,Pageable pageable){
        return generalComplaintService.getComplaintsForRoom(roomId,pageable);
    }

    @GetMapping("/student")
    public ResponseEntity<Page<GeneralComplaintResponse>> getComplaintsStudent(
            @RequestParam Long studentId,Pageable pageable){
        return generalComplaintService.getComplaintsByStudent(studentId,pageable);
    }
    @PatchMapping("/{id}/resolve")
    public ResponseEntity<GeneralComplaintResponse> resolveComplaint(
            @PathVariable Long id,
            @RequestParam String note) {

        return generalComplaintService.resolveComplaint(id, note);
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<GeneralComplaintResponse> rejectComplaint(
            @PathVariable Long id,
            @RequestParam String note) {

        return generalComplaintService.rejectComplaint(id, note);
    }

    @GetMapping("/pending")
    public ResponseEntity<Page<GeneralComplaintResponse>> getPendingComplaints(
            Pageable pageable) {

        return generalComplaintService.getPendingComplaints(pageable);
    }

    @GetMapping("/resolved")
    public ResponseEntity<Page<GeneralComplaintResponse>> getResolvedComplaints(
            Pageable pageable) {

        return generalComplaintService.getResolvedComplaints(pageable);
    }
}

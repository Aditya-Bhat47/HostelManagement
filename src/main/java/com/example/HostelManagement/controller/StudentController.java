package com.example.HostelManagement.controller;

import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.records.FeePaymentRequest;
import com.example.HostelManagement.records.StudentRequest;
import com.example.HostelManagement.service.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.HostelManagement.records.getStudentResp;
import java.util.List;

import java.security.Principal;

@RestController
@RequestMapping("/student")
@Validated
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/me")
    public ResponseEntity<getStudentResp> getStudent(Principal principal) {
        return ResponseEntity.ok(studentService.getStudentData(principal.getName()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('WARDEN','ADMIN')")
    public ResponseEntity<List<getStudentResp>> getAllStudent() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasAnyRole('WARDEN','ADMIN')")
    public ResponseEntity<getStudentResp> getParticularStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getParticularStudent(id));
    }

    @GetMapping("/usn/{usn}")
    @PreAuthorize("hasAnyRole('WARDEN','ADMIN')")
    public ResponseEntity<getStudentResp> getStudentByUsn(
            @PathVariable @Pattern(regexp = "^[0-9][A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{3}$",
                    message = "Enter a valid USN (e.g. 1AB21CS001)") String usn) {
        return ResponseEntity.ok(studentService.getStudentByUsn(usn));
    }

    @GetMapping("/year/{year}")
    @PreAuthorize("hasAnyRole('WARDEN','ADMIN')")
    public ResponseEntity<List<getStudentResp>> getStudentByYear(@PathVariable int year) {
        return ResponseEntity.ok(studentService.getStudentByYear(year));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('WARDEN','ADMIN')")
    public ResponseEntity<getStudentResp> addStudent(@Valid @RequestBody StudentRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(req));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('WARDEN','ADMIN')")
    public ResponseEntity<getStudentResp> updateStudent(@Valid @RequestBody StudentRequest req) {
        return ResponseEntity.ok(studentService.updateStudent(req));
    }

    @DeleteMapping("/usn/{usn}")
    @PreAuthorize("hasAnyRole('WARDEN','ADMIN')")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable @Pattern(regexp = "^[0-9][A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{3}$",
                    message = "Enter a valid USN (e.g. 1AB21CS001)") String usn) {
        studentService.deleteStudent(usn);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/pay-fees")
    @PreAuthorize("hasAnyRole('WARDEN','ADMIN')")
    public ResponseEntity<getStudentResp> payFees(@Valid @RequestBody FeePaymentRequest req) {
        return ResponseEntity.ok(studentService.payFees(req));
    }

    @PutMapping("/{usn}/room/{roomId}")
    @PreAuthorize("hasAnyRole('WARDEN','ADMIN')")
    public ResponseEntity<getStudentResp> assignRoom(@PathVariable String usn, @PathVariable Long roomId) {
        return ResponseEntity.ok(studentService.assignRoom(usn, roomId));
    }

    @DeleteMapping("/{usn}/room")
    @PreAuthorize("hasAnyRole('WARDEN','ADMIN')")
    public ResponseEntity<getStudentResp> vacateRoom(@PathVariable String usn) {
        return ResponseEntity.ok(studentService.vacateRoom(usn));
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('WARDEN','ADMIN')")
    public ResponseEntity<List<getStudentResp>> pendingFees() {
        return ResponseEntity.ok(studentService.getStudentsWithPendingFees());
    }
}

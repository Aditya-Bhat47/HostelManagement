package com.example.HostelManagement.service;

import com.example.HostelManagement.entity.AppUser;
import com.example.HostelManagement.entity.Rooms;
import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.enums.Role;
import com.example.HostelManagement.exceptionHandler.ResourceNotFoundexception;
import com.example.HostelManagement.records.StudentRegistrationRequest;
import com.example.HostelManagement.records.StudentRegistrationResponse;
import com.example.HostelManagement.repository.AppUserRepo;
import com.example.HostelManagement.repository.RoomRepo;
import com.example.HostelManagement.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepo appUserRepo;
    private final StudentRepository studentRepository;
    private final RoomRepo roomRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public StudentRegistrationResponse registerStudent(StudentRegistrationRequest request){
        String email=request.email().trim().toLowerCase(Locale.ROOT);
        if (appUserRepo.existsByEmail(email) || studentRepository.existsByEmail(email)){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,"Email is alredy registered"
            );
        }

        if (studentRepository.existsByUsn(request.usn())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "USN is already registered"
            );
        }

        Rooms room = null;

        if (request.roomId() != null) {
            room = roomRepo.findById(request.roomId())
                    .orElseThrow(() ->
                            new ResourceNotFoundexception("Room not found"));
        }

        BigDecimal fees = request.fees();

        if (fees == null || fees.signum() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Fees must be provided and cannot be negative"
            );
        }

        Student student = new Student();
        student.setName(request.name().trim());
        student.setUsn(request.usn().trim());
        student.setEmail(email);
        student.setPhone(request.phone());
        student.setFees(fees);
        student.setPending(fees);
        student.setRoom(room);

        Student savedStudent = studentRepository.save(student);

        AppUser user = new AppUser();
        user.setEmail(email);
        user.setPassword(
                passwordEncoder.encode(request.password())
        );
        user.setRole(Role.STUDENT);
        user.setStudent(savedStudent);

        AppUser savedUser = appUserRepo.save(user);

        return new StudentRegistrationResponse(
                savedStudent.getId(),
                savedUser.getId(),
                savedStudent.getName(),
                savedStudent.getUsn(),
                savedStudent.getEmail(),
                savedUser.getRole()
        );

    }
}

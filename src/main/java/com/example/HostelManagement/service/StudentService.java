package com.example.HostelManagement.service;

import com.example.HostelManagement.entity.Department;
import com.example.HostelManagement.entity.Rooms;
import com.example.HostelManagement.entity.Student;
import com.example.HostelManagement.exceptionHandler.DepartmentNotFoundException;
import com.example.HostelManagement.exceptionHandler.RoomNotFoundException;
import com.example.HostelManagement.exceptionHandler.StudentNotFoundException;
import com.example.HostelManagement.records.FeePaymentRequest;
import com.example.HostelManagement.records.StudentRequest;
import com.example.HostelManagement.repository.DepartmentRepository;
import com.example.HostelManagement.repository.RoomRepository;
import com.example.HostelManagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import com.example.HostelManagement.records.getStudentResp;
import com.example.HostelManagement.exceptionHandler.StudentAlreadyExistException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    private DepartmentRepository departmentRepository;
    private RoomRepository roomRepository;

    public StudentService(StudentRepository studentRepository,
                          DepartmentRepository departmentRepository,
                          RoomRepository roomRepository){
        this.studentRepository=studentRepository;
        this.departmentRepository=departmentRepository;
        this.roomRepository=roomRepository;
    }

    @Transactional
    public getStudentResp getStudentData(String username) {
        Student student =validateStudent(username);
        return toResponse(student);
    }

    @Transactional
    public List<getStudentResp> getAllStudent(){
        return studentRepository.findAll()
                .stream()
                .map(x->toResponse(x))
                .toList();
    }

    @Transactional
    public getStudentResp getParticularStudent(Long id){
        Student student=validateStudent(id);
        return toResponse(student);
    }

    @Transactional
    public List<getStudentResp> getStudentByYear(int year){
        if (year < 1 || year > 4) {
            throw new IllegalArgumentException("Year must be between 1 and 4");
        }
        return studentRepository.findAll()
                .stream()
                .filter(x->!x.isGraduated())
                .filter(x->x.getRawYear()==year)
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public getStudentResp getStudentByUsn(String usn){
        Student st=studentRepository.findByUsn(usn)
                .orElseThrow(()->new StudentNotFoundException("Student Not Found"));
        return toResponse(st);
    }

    @Transactional
    public getStudentResp addStudent(StudentRequest studentRequest) {

        if (studentRepository.existsByUsn(studentRequest.usn())) {
            throw new StudentAlreadyExistException("Student with this USN already exists");
        }
        if (studentRepository.existsByEmail(studentRequest.email())) {
            throw new StudentAlreadyExistException("Student with this email already exists");
        }

        Department department = departmentRepository.findById(studentRequest.departmentId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));

        Student student = new Student();
        student.setName(studentRequest.name());
        student.setUsn(studentRequest.usn());
        student.setEmail(studentRequest.email());
        student.setPhone(studentRequest.phone());
        student.setFees(studentRequest.fees());
        student.setPending(studentRequest.fees());
        student.setDepartment(department);

        if (studentRequest.roomId() != null) {
            Rooms room = roomRepository.findById(studentRequest.roomId())
                    .orElseThrow(() -> new RoomNotFoundException("Room not found"));
            student.setRoom(room);
        }

        Student saved = studentRepository.save(student);
        return toResponse(saved);
    }

    @Transactional
    public getStudentResp updateStudent(StudentRequest studentRequest){
        if (!studentRepository.existsByUsn(studentRequest.usn())) {
            throw new StudentNotFoundException("Student with this USN does not exists");
        }
        if (!studentRepository.existsByEmail(studentRequest.email())) {
            throw new StudentNotFoundException("Student with this email does not exists");
        }
        Department department = departmentRepository.findById(studentRequest.departmentId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
        Student student = new Student();
        student.setName(studentRequest.name());
        student.setUsn(studentRequest.usn());
        student.setEmail(studentRequest.email());
        student.setPhone(studentRequest.phone());
        student.setFees(studentRequest.fees());
        student.setDepartment(department);
        Student saved = studentRepository.save(student);
        return toResponse(saved);
    }

    @Transactional
    public void deleteStudent(String usn){
        Student st=studentRepository.findByUsn(usn)
                .orElseThrow(()->new StudentNotFoundException("Student Not Found"));
        studentRepository.delete(st);
    }

    @Transactional
    public getStudentResp payFees(FeePaymentRequest req) {

        Student student = studentRepository.findByUsn(req.usn())
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found"));

        if (student.getPending().compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("No pending fees for this student");
        }
        if (req.amount().compareTo(student.getPending()) > 0) {
            throw new IllegalArgumentException(
                    "Amount exceeds pending fees (" + student.getPending() + ")");
        }

        student.setPending(student.getPending().subtract(req.amount()));
        return toResponse(studentRepository.save(student));
    }

    @Transactional
    public getStudentResp assignRoom(String usn, Long roomId) {
        Student student = studentRepository.findByUsn(usn)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found"));

        Rooms room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        long occupied = studentRepository.countByRoomId(roomId);
        boolean alreadyInRoom = student.getRoom() != null && student.getRoom().getId().equals(roomId);

        if (!alreadyInRoom && occupied >= room.getCapacity()) {
            throw new IllegalArgumentException("Room is full");
        }

        student.setRoom(room);
        return toResponse(studentRepository.save(student));
    }

    @Transactional(readOnly = true)
    public List<getStudentResp> getStudentsWithPendingFees() {
        return studentRepository.findByPendingGreaterThan(BigDecimal.ZERO)
                .stream().map(this::toResponse).toList();
    }

    @Transactional
    public getStudentResp vacateRoom(String usn) {
        Student student = studentRepository.findByUsn(usn)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found"));
        student.setRoom(null);
        return toResponse(studentRepository.save(student));
    }

    private Student validateStudent(String username){
        return studentRepository.findByName(username)
                .orElseThrow(()->new StudentNotFoundException("Student Not Found"));
    }

    private Student validateStudent(Long id){
        return studentRepository.findById(id)
                .orElseThrow(()->new StudentNotFoundException("Student Not Found"));
    }
    private getStudentResp toResponse(Student student){
        return new getStudentResp(
                student.getName(),
                student.getUsn(),
                student.getEmail(),
                student.getPhone(),
                student.getDepartment(),
                student.getFees(),
                student.getPending(),
                student.getRoom()
        );
    }

}

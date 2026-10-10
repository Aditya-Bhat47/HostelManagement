package com.example.HostelManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false,unique = true)
    @Pattern(regexp = "^[0-9][A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{3}$",message = "Enter valid Usn")
    private String usn;

    @Column(nullable = false,unique = true)
    @Email
    private String email;

    @Column(nullable = false,length = 10)
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Enter a valid 10-digit Indian mobile number")
    private Long phone;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime admissionDate;

    @Column(nullable = false)
    private BigDecimal fees;

    @Column(nullable = false)
    private BigDecimal pending;

    public Student(String name, String usn) {
        this.name = name;
        this.usn = usn;
    }

    @Transient
    public int getCurrentYear() {
        if (admissionDate == null) return 1;
        return Math.max(1, Math.min(getRawYear(), 4));
    }

    @Transient
    public boolean isGraduated() {
        return admissionDate != null && getRawYear() > 4;
    }

    private int getRawYear() {
        int joined = academicStartYear(admissionDate.toLocalDate());
        int now = academicStartYear(LocalDate.now());
        return now - joined + 1;
    }

    private static int academicStartYear(LocalDate date) {
        return date.getMonthValue() >= 8 ? date.getYear() : date.getYear() - 1;
    }
}

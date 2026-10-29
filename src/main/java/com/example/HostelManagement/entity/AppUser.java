package com.example.HostelManagement.entity;

import com.example.HostelManagement.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Enumerated(EnumType.STRING)
    Role role;

    @Email
    @NotBlank
    String email;

    @NotBlank
            @Column(nullable = false)
    String password;

    @OneToOne
    @JoinColumn(name = "student_id",unique = true)
    Student student;
}

package com.kirillus.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", length = 200, nullable = false)
    private String fullName;

    @Column(name = "position", columnDefinition = "TEXT")
    private String position;

    @Column(name = "employee_number", length = 6, unique = true, nullable = false)
    private String employeeNumber;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeePhoto photo;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}


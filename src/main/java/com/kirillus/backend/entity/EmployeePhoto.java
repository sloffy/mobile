package com.kirillus.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_photos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "filename", length = 255, nullable = false)
    private String filename;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false, unique = true)
    private Employee employee;
}


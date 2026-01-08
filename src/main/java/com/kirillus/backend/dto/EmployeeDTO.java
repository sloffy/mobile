package com.kirillus.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Integer id;
    private String fullName;
    private String position;
    private String employeeNumber;
    private LocalDateTime createdAt;
    private String photoFilename;
}


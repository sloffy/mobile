package com.kirillus.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRecorderReturnDTO {
    private Integer id;
    private Integer videoRecorderId;
    private String videoRecorderNumber;
    private Integer employeeId;
    private String employeeFullName;
    private String employeeNumber;
    private Integer returnedByUserId;
    private String returnedByUserName;
    private LocalDateTime returnDate;
}


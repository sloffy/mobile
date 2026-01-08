package com.kirillus.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRecorderIssueDTO {
    private Integer id;
    private Integer videoRecorderId;
    private String videoRecorderNumber;
    private Integer employeeId;
    private String employeeFullName;
    private String employeeNumber;
    private Integer issuedByUserId;
    private String issuedByUserName;
    private LocalDateTime issueDate;
    private String status;
}


package com.kirillus.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryItemDTO {
    private String type; // "issue" или "return"
    private Integer id;
    private Integer videoRecorderId;
    private String videoRecorderNumber;
    private Integer employeeId;
    private String employeeFullName;
    private String employeeNumber;
    private Integer userId;
    private String userName;
    private LocalDateTime date;
    private String status; // только для issue
}


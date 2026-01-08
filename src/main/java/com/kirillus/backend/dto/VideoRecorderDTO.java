package com.kirillus.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRecorderDTO {
    private Integer id;
    private String number;
    private String status;
    private LocalDateTime createdAt;
}


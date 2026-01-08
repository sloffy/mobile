package com.kirillus.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "video_recorders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRecorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number", length = 100, unique = true, nullable = false)
    private String number;

    @Column(name = "status", length = 20, nullable = false)
    private String status; // available или issued

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = "available";
        }
    }
}


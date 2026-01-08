package com.kirillus.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "video_recorder_returns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRecorderReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "video_recorder_id", nullable = false)
    private VideoRecorder videoRecorder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "returned_by_user_id", nullable = false)
    private User returnedByUser;

    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    @PrePersist
    protected void onCreate() {
        returnDate = LocalDateTime.now();
    }
}


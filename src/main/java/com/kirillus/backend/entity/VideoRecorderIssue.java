package com.kirillus.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "video_recorder_issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRecorderIssue {
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
    @JoinColumn(name = "issued_by_user_id", nullable = false)
    private User issuedByUser;

    @Column(name = "issue_date", nullable = false)
    private LocalDateTime issueDate;

    @Column(name = "status", length = 20, nullable = false)
    private String status; // returned или issued

    @PrePersist
    protected void onCreate() {
        issueDate = LocalDateTime.now();
        if (status == null) {
            status = "issued";
        }
    }
}


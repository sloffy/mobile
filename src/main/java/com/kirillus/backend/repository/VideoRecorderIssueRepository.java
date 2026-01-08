package com.kirillus.backend.repository;

import com.kirillus.backend.entity.VideoRecorderIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRecorderIssueRepository extends JpaRepository<VideoRecorderIssue, Integer> {
    List<VideoRecorderIssue> findByVideoRecorderId(Integer videoRecorderId);
    List<VideoRecorderIssue> findByEmployeeId(Integer employeeId);
    List<VideoRecorderIssue> findByStatus(String status);
    Optional<VideoRecorderIssue> findFirstByVideoRecorderIdAndStatusOrderByIssueDateDesc(Integer videoRecorderId, String status);
}


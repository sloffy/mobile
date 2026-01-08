package com.kirillus.backend.repository;

import com.kirillus.backend.entity.VideoRecorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRecorderRepository extends JpaRepository<VideoRecorder, Integer> {
    Optional<VideoRecorder> findByNumber(String number);
    List<VideoRecorder> findByStatus(String status);
}


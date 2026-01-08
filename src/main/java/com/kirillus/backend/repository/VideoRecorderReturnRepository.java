package com.kirillus.backend.repository;

import com.kirillus.backend.entity.VideoRecorderReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRecorderReturnRepository extends JpaRepository<VideoRecorderReturn, Integer> {
    List<VideoRecorderReturn> findByVideoRecorderId(Integer videoRecorderId);
    List<VideoRecorderReturn> findByEmployeeId(Integer employeeId);
}


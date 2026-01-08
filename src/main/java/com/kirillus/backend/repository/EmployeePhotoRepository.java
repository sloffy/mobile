package com.kirillus.backend.repository;

import com.kirillus.backend.entity.EmployeePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeePhotoRepository extends JpaRepository<EmployeePhoto, Integer> {
    Optional<EmployeePhoto> findByEmployeeId(Integer employeeId);
}


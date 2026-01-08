package com.kirillus.backend.service;

import com.kirillus.backend.dto.VideoRecorderIssueDTO;
import com.kirillus.backend.entity.*;
import com.kirillus.backend.repository.*;
import com.kirillus.backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueService {

    @Autowired
    private VideoRecorderIssueRepository issueRepository;

    @Autowired
    private VideoRecorderRepository videoRecorderRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public VideoRecorderIssueDTO issueVideoRecorder(Integer videoRecorderId, Integer employeeId) {
        VideoRecorder recorder = videoRecorderRepository.findById(videoRecorderId)
                .orElseThrow(() -> new RuntimeException("Видеорегистратор не найден"));

        if (!"available".equals(recorder.getStatus())) {
            throw new RuntimeException("Видеорегистратор уже выдан");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден"));

        Integer currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        VideoRecorderIssue issue = new VideoRecorderIssue();
        issue.setVideoRecorder(recorder);
        issue.setEmployee(employee);
        issue.setIssuedByUser(user);
        issue.setStatus("issued");

        // Обновляем статус видеорегистратора
        recorder.setStatus("issued");

        issue = issueRepository.save(issue);
        videoRecorderRepository.save(recorder);

        return toDTO(issue);
    }

    public List<VideoRecorderIssueDTO> getAllIssues() {
        return issueRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<VideoRecorderIssueDTO> getIssuesByVideoRecorder(Integer videoRecorderId) {
        return issueRepository.findByVideoRecorderId(videoRecorderId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<VideoRecorderIssueDTO> getIssuesByEmployee(Integer employeeId) {
        return issueRepository.findByEmployeeId(employeeId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VideoRecorderIssueDTO getIssueById(Integer id) {
        VideoRecorderIssue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Запись о выдаче не найдена"));
        return toDTO(issue);
    }

    private VideoRecorderIssueDTO toDTO(VideoRecorderIssue issue) {
        VideoRecorderIssueDTO dto = new VideoRecorderIssueDTO();
        dto.setId(issue.getId());
        dto.setVideoRecorderId(issue.getVideoRecorder().getId());
        dto.setVideoRecorderNumber(issue.getVideoRecorder().getNumber());
        dto.setEmployeeId(issue.getEmployee().getId());
        dto.setEmployeeFullName(issue.getEmployee().getFullName());
        dto.setEmployeeNumber(issue.getEmployee().getEmployeeNumber());
        dto.setIssuedByUserId(issue.getIssuedByUser().getId());
        dto.setIssuedByUserName(issue.getIssuedByUser().getLastName() + " " + 
                                issue.getIssuedByUser().getFirstName());
        dto.setIssueDate(issue.getIssueDate());
        dto.setStatus(issue.getStatus());
        return dto;
    }
}


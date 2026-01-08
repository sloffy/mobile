package com.kirillus.backend.service;

import com.kirillus.backend.dto.HistoryItemDTO;
import com.kirillus.backend.entity.VideoRecorderIssue;
import com.kirillus.backend.entity.VideoRecorderReturn;
import com.kirillus.backend.repository.VideoRecorderIssueRepository;
import com.kirillus.backend.repository.VideoRecorderReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    @Autowired
    private VideoRecorderIssueRepository issueRepository;

    @Autowired
    private VideoRecorderReturnRepository returnRepository;

    public List<HistoryItemDTO> getAllHistory() {
        List<HistoryItemDTO> history = new ArrayList<>();

        // Добавляем выдачи
        List<HistoryItemDTO> issues = issueRepository.findAll().stream()
                .map(this::issueToHistoryDTO)
                .collect(Collectors.toList());
        history.addAll(issues);

        // Добавляем возвраты
        List<HistoryItemDTO> returns = returnRepository.findAll().stream()
                .map(this::returnToHistoryDTO)
                .collect(Collectors.toList());
        history.addAll(returns);

        // Сортируем по дате (от новых к старым)
        history.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        return history;
    }

    public List<HistoryItemDTO> getHistoryByVideoRecorder(Integer videoRecorderId) {
        List<HistoryItemDTO> history = new ArrayList<>();

        List<HistoryItemDTO> issues = issueRepository.findByVideoRecorderId(videoRecorderId).stream()
                .map(this::issueToHistoryDTO)
                .collect(Collectors.toList());
        history.addAll(issues);

        List<HistoryItemDTO> returns = returnRepository.findByVideoRecorderId(videoRecorderId).stream()
                .map(this::returnToHistoryDTO)
                .collect(Collectors.toList());
        history.addAll(returns);

        history.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        return history;
    }

    public List<HistoryItemDTO> getHistoryByEmployee(Integer employeeId) {
        List<HistoryItemDTO> history = new ArrayList<>();

        List<HistoryItemDTO> issues = issueRepository.findByEmployeeId(employeeId).stream()
                .map(this::issueToHistoryDTO)
                .collect(Collectors.toList());
        history.addAll(issues);

        List<HistoryItemDTO> returns = returnRepository.findByEmployeeId(employeeId).stream()
                .map(this::returnToHistoryDTO)
                .collect(Collectors.toList());
        history.addAll(returns);

        history.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        return history;
    }

    private HistoryItemDTO issueToHistoryDTO(VideoRecorderIssue issue) {
        HistoryItemDTO dto = new HistoryItemDTO();
        dto.setType("issue");
        dto.setId(issue.getId());
        dto.setVideoRecorderId(issue.getVideoRecorder().getId());
        dto.setVideoRecorderNumber(issue.getVideoRecorder().getNumber());
        dto.setEmployeeId(issue.getEmployee().getId());
        dto.setEmployeeFullName(issue.getEmployee().getFullName());
        dto.setEmployeeNumber(issue.getEmployee().getEmployeeNumber());
        dto.setUserId(issue.getIssuedByUser().getId());
        dto.setUserName(issue.getIssuedByUser().getLastName() + " " + 
                       issue.getIssuedByUser().getFirstName());
        dto.setDate(issue.getIssueDate());
        dto.setStatus(issue.getStatus());
        return dto;
    }

    private HistoryItemDTO returnToHistoryDTO(VideoRecorderReturn returnRecord) {
        HistoryItemDTO dto = new HistoryItemDTO();
        dto.setType("return");
        dto.setId(returnRecord.getId());
        dto.setVideoRecorderId(returnRecord.getVideoRecorder().getId());
        dto.setVideoRecorderNumber(returnRecord.getVideoRecorder().getNumber());
        dto.setEmployeeId(returnRecord.getEmployee().getId());
        dto.setEmployeeFullName(returnRecord.getEmployee().getFullName());
        dto.setEmployeeNumber(returnRecord.getEmployee().getEmployeeNumber());
        dto.setUserId(returnRecord.getReturnedByUser().getId());
        dto.setUserName(returnRecord.getReturnedByUser().getLastName() + " " + 
                       returnRecord.getReturnedByUser().getFirstName());
        dto.setDate(returnRecord.getReturnDate());
        return dto;
    }
}


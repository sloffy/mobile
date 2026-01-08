package com.kirillus.backend.service;

import com.kirillus.backend.dto.VideoRecorderReturnDTO;
import com.kirillus.backend.entity.*;
import com.kirillus.backend.repository.*;
import com.kirillus.backend.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReturnService {

    @Autowired
    private VideoRecorderReturnRepository returnRepository;

    @Autowired
    private VideoRecorderIssueRepository issueRepository;

    @Autowired
    private VideoRecorderRepository videoRecorderRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public VideoRecorderReturnDTO returnVideoRecorder(Integer videoRecorderId, Integer employeeId) {
        VideoRecorder recorder = videoRecorderRepository.findById(videoRecorderId)
                .orElseThrow(() -> new RuntimeException("Видеорегистратор не найден"));

        if (!"issued".equals(recorder.getStatus())) {
            throw new RuntimeException("Видеорегистратор не был выдан");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден"));

        // Проверяем, что видеорегистратор был выдан именно этому сотруднику
        VideoRecorderIssue activeIssue = issueRepository
                .findFirstByVideoRecorderIdAndStatusOrderByIssueDateDesc(videoRecorderId, "issued")
                .orElseThrow(() -> new RuntimeException("Не найдена активная выдача для этого видеорегистратора"));

        if (!activeIssue.getEmployee().getId().equals(employeeId)) {
            throw new RuntimeException("Видеорегистратор выдан другому сотруднику");
        }

        Integer currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Создаем запись о возврате
        VideoRecorderReturn returnRecord = new VideoRecorderReturn();
        returnRecord.setVideoRecorder(recorder);
        returnRecord.setEmployee(employee);
        returnRecord.setReturnedByUser(user);

        // Обновляем статус видеорегистратора
        recorder.setStatus("available");

        // Обновляем статус выдачи
        activeIssue.setStatus("returned");

        returnRecord = returnRepository.save(returnRecord);
        videoRecorderRepository.save(recorder);
        issueRepository.save(activeIssue);

        return toDTO(returnRecord);
    }

    public List<VideoRecorderReturnDTO> getAllReturns() {
        return returnRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<VideoRecorderReturnDTO> getReturnsByVideoRecorder(Integer videoRecorderId) {
        return returnRepository.findByVideoRecorderId(videoRecorderId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<VideoRecorderReturnDTO> getReturnsByEmployee(Integer employeeId) {
        return returnRepository.findByEmployeeId(employeeId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private VideoRecorderReturnDTO toDTO(VideoRecorderReturn returnRecord) {
        VideoRecorderReturnDTO dto = new VideoRecorderReturnDTO();
        dto.setId(returnRecord.getId());
        dto.setVideoRecorderId(returnRecord.getVideoRecorder().getId());
        dto.setVideoRecorderNumber(returnRecord.getVideoRecorder().getNumber());
        dto.setEmployeeId(returnRecord.getEmployee().getId());
        dto.setEmployeeFullName(returnRecord.getEmployee().getFullName());
        dto.setEmployeeNumber(returnRecord.getEmployee().getEmployeeNumber());
        dto.setReturnedByUserId(returnRecord.getReturnedByUser().getId());
        dto.setReturnedByUserName(returnRecord.getReturnedByUser().getLastName() + " " + 
                                  returnRecord.getReturnedByUser().getFirstName());
        dto.setReturnDate(returnRecord.getReturnDate());
        return dto;
    }
}


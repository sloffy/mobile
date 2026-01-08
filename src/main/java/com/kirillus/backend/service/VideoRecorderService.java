package com.kirillus.backend.service;

import com.kirillus.backend.dto.VideoRecorderDTO;
import com.kirillus.backend.entity.VideoRecorder;
import com.kirillus.backend.repository.VideoRecorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoRecorderService {

    @Autowired
    private VideoRecorderRepository videoRecorderRepository;

    public List<VideoRecorderDTO> getAllVideoRecorders() {
        return videoRecorderRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<VideoRecorderDTO> getVideoRecordersByStatus(String status) {
        return videoRecorderRepository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VideoRecorderDTO getVideoRecorderById(Integer id) {
        VideoRecorder recorder = videoRecorderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Видеорегистратор не найден"));
        return toDTO(recorder);
    }

    @Transactional
    public VideoRecorderDTO createVideoRecorder(VideoRecorderDTO dto) {
        if (videoRecorderRepository.findByNumber(dto.getNumber()).isPresent()) {
            throw new RuntimeException("Видеорегистратор с таким номером уже существует");
        }

        VideoRecorder recorder = new VideoRecorder();
        recorder.setNumber(dto.getNumber());
        recorder.setStatus(dto.getStatus() != null ? dto.getStatus() : "available");

        recorder = videoRecorderRepository.save(recorder);
        return toDTO(recorder);
    }

    @Transactional
    public VideoRecorderDTO updateVideoRecorder(Integer id, VideoRecorderDTO dto) {
        VideoRecorder recorder = videoRecorderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Видеорегистратор не найден"));

        // Проверка уникальности номера
        videoRecorderRepository.findByNumber(dto.getNumber())
                .ifPresent(vr -> {
                    if (!vr.getId().equals(id)) {
                        throw new RuntimeException("Видеорегистратор с таким номером уже существует");
                    }
                });

        recorder.setNumber(dto.getNumber());
        if (dto.getStatus() != null) {
            recorder.setStatus(dto.getStatus());
        }

        recorder = videoRecorderRepository.save(recorder);
        return toDTO(recorder);
    }

    @Transactional
    public void deleteVideoRecorder(Integer id) {
        VideoRecorder recorder = videoRecorderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Видеорегистратор не найден"));

        if ("issued".equals(recorder.getStatus())) {
            throw new RuntimeException("Нельзя удалить выданный видеорегистратор");
        }

        videoRecorderRepository.deleteById(id);
    }

    private VideoRecorderDTO toDTO(VideoRecorder recorder) {
        VideoRecorderDTO dto = new VideoRecorderDTO();
        dto.setId(recorder.getId());
        dto.setNumber(recorder.getNumber());
        dto.setStatus(recorder.getStatus());
        dto.setCreatedAt(recorder.getCreatedAt());
        return dto;
    }
}


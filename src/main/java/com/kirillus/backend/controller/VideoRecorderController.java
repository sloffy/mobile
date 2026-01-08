package com.kirillus.backend.controller;

import com.kirillus.backend.dto.VideoRecorderDTO;
import com.kirillus.backend.service.VideoRecorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video-recorders")
@CrossOrigin(origins = "*")
public class VideoRecorderController {

    @Autowired
    private VideoRecorderService videoRecorderService;

    @GetMapping
    public ResponseEntity<List<VideoRecorderDTO>> getAllVideoRecorders(
            @RequestParam(required = false) String status) {
        if (status != null) {
            return ResponseEntity.ok(videoRecorderService.getVideoRecordersByStatus(status));
        }
        return ResponseEntity.ok(videoRecorderService.getAllVideoRecorders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoRecorderDTO> getVideoRecorderById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(videoRecorderService.getVideoRecorderById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<VideoRecorderDTO> createVideoRecorder(@RequestBody VideoRecorderDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(videoRecorderService.createVideoRecorder(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoRecorderDTO> updateVideoRecorder(@PathVariable Integer id, @RequestBody VideoRecorderDTO dto) {
        try {
            return ResponseEntity.ok(videoRecorderService.updateVideoRecorder(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoRecorder(@PathVariable Integer id) {
        try {
            videoRecorderService.deleteVideoRecorder(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}


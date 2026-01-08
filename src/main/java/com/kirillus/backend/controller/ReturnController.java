package com.kirillus.backend.controller;

import com.kirillus.backend.dto.VideoRecorderReturnDTO;
import com.kirillus.backend.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/returns")
@CrossOrigin(origins = "*")
public class ReturnController {

    @Autowired
    private ReturnService returnService;

    @GetMapping
    public ResponseEntity<List<VideoRecorderReturnDTO>> getAllReturns() {
        return ResponseEntity.ok(returnService.getAllReturns());
    }

    @GetMapping("/video-recorder/{videoRecorderId}")
    public ResponseEntity<List<VideoRecorderReturnDTO>> getReturnsByVideoRecorder(@PathVariable Integer videoRecorderId) {
        return ResponseEntity.ok(returnService.getReturnsByVideoRecorder(videoRecorderId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<VideoRecorderReturnDTO>> getReturnsByEmployee(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(returnService.getReturnsByEmployee(employeeId));
    }

    @PostMapping
    public ResponseEntity<VideoRecorderReturnDTO> returnVideoRecorder(@RequestBody Map<String, Integer> request) {
        try {
            Integer videoRecorderId = request.get("videoRecorderId");
            Integer employeeId = request.get("employeeId");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(returnService.returnVideoRecorder(videoRecorderId, employeeId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}


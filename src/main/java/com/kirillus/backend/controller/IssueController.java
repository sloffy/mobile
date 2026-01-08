package com.kirillus.backend.controller;

import com.kirillus.backend.dto.VideoRecorderIssueDTO;
import com.kirillus.backend.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "*")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @GetMapping
    public ResponseEntity<List<VideoRecorderIssueDTO>> getAllIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoRecorderIssueDTO> getIssueById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(issueService.getIssueById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/video-recorder/{videoRecorderId}")
    public ResponseEntity<List<VideoRecorderIssueDTO>> getIssuesByVideoRecorder(@PathVariable Integer videoRecorderId) {
        return ResponseEntity.ok(issueService.getIssuesByVideoRecorder(videoRecorderId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<VideoRecorderIssueDTO>> getIssuesByEmployee(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(issueService.getIssuesByEmployee(employeeId));
    }

    @PostMapping
    public ResponseEntity<VideoRecorderIssueDTO> issueVideoRecorder(@RequestBody Map<String, Integer> request) {
        try {
            Integer videoRecorderId = request.get("videoRecorderId");
            Integer employeeId = request.get("employeeId");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(issueService.issueVideoRecorder(videoRecorderId, employeeId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}


package com.kirillus.backend.controller;

import com.kirillus.backend.dto.HistoryItemDTO;
import com.kirillus.backend.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "*")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public ResponseEntity<List<HistoryItemDTO>> getAllHistory() {
        return ResponseEntity.ok(historyService.getAllHistory());
    }

    @GetMapping("/video-recorder/{videoRecorderId}")
    public ResponseEntity<List<HistoryItemDTO>> getHistoryByVideoRecorder(@PathVariable Integer videoRecorderId) {
        return ResponseEntity.ok(historyService.getHistoryByVideoRecorder(videoRecorderId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<HistoryItemDTO>> getHistoryByEmployee(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(historyService.getHistoryByEmployee(employeeId));
    }
}


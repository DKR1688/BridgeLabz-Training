package com.bridgelabz.notes.controller;

import com.bridgelabz.notes.dto.LabelRequest;
import com.bridgelabz.notes.dto.LabelResponse;
import com.bridgelabz.notes.service.LabelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/labels")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @PostMapping
    public ResponseEntity<LabelResponse> createLabel(@Valid @RequestBody LabelRequest request) {
        LabelResponse response = labelService.createLabel(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LabelResponse>> getAllLabels() {
        return ResponseEntity.ok(labelService.getAllLabels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabelResponse> getLabelById(@PathVariable("id") int labelId) {
        return ResponseEntity.ok(labelService.getLabelById(labelId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabelResponse> updateLabel(@PathVariable("id") int labelId, @Valid @RequestBody LabelRequest request) {
        return ResponseEntity.ok(labelService.updateLabel(labelId, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteLabel(@PathVariable("id") int labelId) {
        labelService.deleteLabel(labelId);
        return ResponseEntity.ok(Map.of("message", "Label deleted successfully"));
    }
}

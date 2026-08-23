package com.bridgelabz.fundoonotes.controller;

import com.bridgelabz.fundoonotes.dto.LabelRequest;
import com.bridgelabz.fundoonotes.dto.LabelResponse;
import com.bridgelabz.fundoonotes.entity.Tag;
import com.bridgelabz.fundoonotes.exception.LabelNotFoundException;
import com.bridgelabz.fundoonotes.service.LabelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/noteLabels")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    private int currentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            throw new IllegalArgumentException("Unauthorized");
        }
        return Integer.parseInt(principal.toString());
    }

    @PostMapping
    public ResponseEntity<LabelResponse> createLabel(@Valid @RequestBody LabelRequest request) {
        Tag tag = labelService.createLabel(currentUserId(), request.resolvedLabel());
        return ResponseEntity.status(HttpStatus.CREATED).body(LabelResponse.fromEntity(tag));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LabelResponse> patchLabel(@PathVariable int id, @Valid @RequestBody LabelRequest request) {
        Tag tag = labelService.updateLabel(id, currentUserId(), request.resolvedLabel());
        return ResponseEntity.ok(LabelResponse.fromEntity(tag));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabelResponse> updateLabel(@PathVariable int id, @Valid @RequestBody LabelRequest request) {
        Tag tag = labelService.updateLabel(id, currentUserId(), request.resolvedLabel());
        return ResponseEntity.ok(LabelResponse.fromEntity(tag));
    }

    @DeleteMapping("/{id}/deleteNoteLabel")
    public ResponseEntity<Void> deleteNoteLabel(@PathVariable int id) {
        boolean deleted = labelService.softDeleteLabel(id, currentUserId());
        if (!deleted) {
            throw new LabelNotFoundException("Label not found");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabel(@PathVariable int id) {
        boolean deleted = labelService.softDeleteLabel(id, currentUserId());
        if (!deleted) {
            throw new LabelNotFoundException("Label not found");
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping({ "/getNoteLabelList", "" })
    public List<LabelResponse> getNoteLabelList() {
        return labelService.getActiveLabels(currentUserId())
                .stream()
                .map(LabelResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabelResponse> getLabelById(@PathVariable int id) {
        return labelService.getLabelById(id, currentUserId())
                .map(tag -> ResponseEntity.ok(LabelResponse.fromEntity(tag)))
                .orElseThrow(() -> new LabelNotFoundException("Label not found"));
    }
}

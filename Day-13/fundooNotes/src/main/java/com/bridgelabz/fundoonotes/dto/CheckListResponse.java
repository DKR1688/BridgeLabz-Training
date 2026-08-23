package com.bridgelabz.fundoonotes.dto;

import com.bridgelabz.fundoonotes.entity.NoteCheckList;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record CheckListResponse(
        int id,
        String itemName,
        String status,
        boolean isDeleted,
        int notesId,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime updatedAt) {
    public static CheckListResponse fromEntity(NoteCheckList item) {
        if (item == null)
            return null;
        int noteId = (item.getNote() != null) ? item.getNote().getNoteId() : 0;
        return new CheckListResponse(
                item.getId(),
                item.getItemName(),
                item.getStatus(),
                item.isDeleted(),
                noteId,
                item.getCreatedAt(),
                item.getUpdatedAt());
    }
}

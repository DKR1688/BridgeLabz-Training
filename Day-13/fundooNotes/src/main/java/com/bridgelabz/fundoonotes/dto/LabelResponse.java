package com.bridgelabz.fundoonotes.dto;

import com.bridgelabz.fundoonotes.entity.Tag;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LabelResponse(
        int id,
        String label,
        boolean isDeleted,
        String userId) {
    public static LabelResponse fromEntity(Tag tag) {
        String userIdStr = (tag.getOwner() != null) ? String.valueOf(tag.getOwner().getUserId()) : null;
        return new LabelResponse(
                tag.getTagId(),
                tag.getName(),
                tag.isDeleted(),
                userIdStr);
    }
}

package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class NoteSpecifications {

    public static Specification<Note> search(
            User owner,
            String titleText,
            String state,
            String labelName) {
        Note.NoteState noteState = null;
        if (state != null && !state.isBlank()) {
            try {
                noteState = Note.NoteState.valueOf(state.trim().toUpperCase());
            } catch (IllegalArgumentException ignored) {
            }
        }
        return search(owner, titleText, noteState, labelName, null);
    }

    public static Specification<Note> search(
            User owner,
            String titleText,
            Note.NoteState state,
            String tagName) {
        return search(owner, titleText, state, tagName, null);
    }

    public static Specification<Note> search(
            User owner,
            String titleText,
            Note.NoteState state,
            String tagName,
            Boolean pinned) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // ALWAYS required: owner predicate ensures multi-tenant authorization boundary
            predicates.add(criteriaBuilder.equal(root.get("owner"), owner));

            if (titleText != null && !titleText.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + titleText.toLowerCase().trim() + "%"));
            }

            if (state != null) {
                predicates.add(criteriaBuilder.equal(root.get("state"), state));
            }

            if (tagName != null && !tagName.isBlank()) {
                if (query != null) {
                    query.distinct(true);
                }
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.join("tags").get("name")),
                        tagName.toLowerCase().trim()));
            }

            if (pinned != null) {
                predicates.add(criteriaBuilder.equal(root.get("pinned"), pinned));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

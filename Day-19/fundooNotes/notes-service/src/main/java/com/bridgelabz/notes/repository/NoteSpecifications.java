package com.bridgelabz.notes.repository;

import com.bridgelabz.notes.entity.Note;
import com.bridgelabz.notes.entity.Tag;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class NoteSpecifications {

    public static Specification<Note> filterNotes(
            int userId,
            String query,
            Note.NoteState state,
            Boolean pinned,
            String tag,
            String color) {

        return (root, criteriaQuery, cb) -> {
            criteriaQuery.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            // User ownership
            predicates.add(cb.equal(root.get("ownerId"), userId));

            // State filter
            if (state != null) {
                predicates.add(cb.equal(root.get("state"), state));
            }

            // Pinned filter
            if (pinned != null) {
                predicates.add(cb.equal(root.get("pinned"), pinned));
            }

            // Color filter
            if (color != null && !color.trim().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("color")), color.trim().toLowerCase()));
            }

            // Tag/Label filter
            if (tag != null && !tag.trim().isEmpty()) {
                Join<Note, Tag> tagJoin = root.join("tags");
                predicates.add(cb.equal(cb.lower(tagJoin.get("name")), tag.trim().toLowerCase()));
            }

            // Text search (title or content)
            if (query != null && !query.trim().isEmpty()) {
                String searchPattern = "%" + query.trim().toLowerCase() + "%";
                Predicate titleMatch = cb.like(cb.lower(root.get("title")), searchPattern);
                Predicate contentMatch = cb.like(cb.lower(root.get("content")), searchPattern);
                predicates.add(cb.or(titleMatch, contentMatch));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer>, JpaSpecificationExecutor<Note> {

    @EntityGraph(attributePaths = { "tags" })
    List<Note> findByOwner(User owner);

    @EntityGraph(attributePaths = { "tags" })
    Optional<Note> findByNoteIdAndOwner(int noteId, User owner);

    @EntityGraph(attributePaths = { "tags" })
    Optional<Note> findByNoteIdAndOwner_UserId(int noteId, int userId);

    @EntityGraph(attributePaths = { "tags" })
    List<Note> findByOwnerAndState(User owner, Note.NoteState state);

    @EntityGraph(attributePaths = { "tags" })
    List<Note> findByOwnerAndPinnedTrueAndStateNot(User owner, Note.NoteState excludedState);

    @EntityGraph(attributePaths = { "tags" })
    List<Note> findByOwnerAndPinnedTrueAndState(User owner, Note.NoteState state);

    @EntityGraph(attributePaths = { "tags" })
    List<Note> findByOwnerAndTagsName(User owner, String tagName);

    @EntityGraph(attributePaths = { "tags" })
    List<Note> findByOwnerAndTagsNameAndTagsIsDeletedFalse(User owner, String tagName);

    @Override
    @EntityGraph(attributePaths = { "tags" })
    List<Note> findAll(Specification<Note> spec);

    @Query("SELECT n FROM Note n LEFT JOIN FETCH n.tags WHERE n.noteId = :noteId AND n.owner = :owner")
    Optional<Note> findByNoteIdAndOwnerWithTags(@Param("noteId") int noteId, @Param("owner") User owner);

    @Query("SELECT DISTINCT n FROM Note n LEFT JOIN FETCH n.tags WHERE n.owner = :owner")
    List<Note> findByOwnerWithTags(@Param("owner") User owner);

    @Query("SELECT DISTINCT n FROM Note n WHERE n.owner = :owner AND SIZE(n.reminders) > 0")
    List<Note> findNotesWithRemindersByOwner(@Param("owner") User owner);
}

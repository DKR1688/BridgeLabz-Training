package com.bridgelabz.notes.repository;

import com.bridgelabz.notes.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer>, JpaSpecificationExecutor<Note> {

    List<Note> findByOwnerId(int ownerId);

    List<Note> findByOwnerIdAndState(int ownerId, Note.NoteState state);

    List<Note> findByOwnerIdAndPinned(int ownerId, boolean pinned);

    List<Note> findByOwnerIdAndStateAndPinned(int ownerId, Note.NoteState state, boolean pinned);

    List<Note> findByOwnerIdAndStateOrderByCreatedAtDesc(int ownerId, Note.NoteState state);

    @Query("SELECT n FROM Note n LEFT JOIN FETCH n.tags WHERE n.noteId = :noteId")
    Optional<Note> findWithTagsByNoteId(@Param("noteId") int noteId);

    @Query("SELECT DISTINCT n FROM Note n LEFT JOIN FETCH n.tags LEFT JOIN FETCH n.checkLists WHERE n.noteId = :noteId")
    Optional<Note> findWithTagsAndCheckListsByNoteId(@Param("noteId") int noteId);

    @Query("SELECT DISTINCT n FROM Note n LEFT JOIN FETCH n.tags LEFT JOIN FETCH n.checkLists LEFT JOIN n.collaboratorIds c WHERE n.noteId = :noteId AND (n.ownerId = :userId OR c = :userId)")
    Optional<Note> findAccessibleNoteWithDetails(@Param("noteId") int noteId, @Param("userId") int userId);

    @Query("SELECT DISTINCT n FROM Note n LEFT JOIN FETCH n.tags LEFT JOIN n.collaboratorIds c WHERE n.ownerId = :userId OR c = :userId")
    List<Note> findAllAccessibleNotes(@Param("userId") int userId);

    @Query("SELECT DISTINCT n FROM Note n LEFT JOIN n.collaboratorIds c WHERE n.noteId = :noteId AND (n.ownerId = :userId OR c = :userId)")
    Optional<Note> findAccessibleNote(@Param("noteId") int noteId, @Param("userId") int userId);

    @Query("SELECT DISTINCT n FROM Note n JOIN FETCH n.tags t WHERE (n.ownerId = :userId) AND LOWER(t.name) = LOWER(:labelName)")
    List<Note> findByUserIdAndLabelName(@Param("userId") int userId, @Param("labelName") String labelName);
}

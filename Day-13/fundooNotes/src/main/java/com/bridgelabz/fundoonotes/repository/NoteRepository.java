package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findByOwner(User owner);

    Optional<Note> findByNoteIdAndOwner(int noteId, User owner);

    Optional<Note> findByNoteIdAndOwner_UserId(int noteId, int userId);

    @Query("SELECT n FROM Note n LEFT JOIN FETCH n.tags WHERE n.noteId = :noteId AND n.owner = :owner")
    Optional<Note> findByNoteIdAndOwnerWithTags(@Param("noteId") int noteId, @Param("owner") User owner);

    @Query("SELECT DISTINCT n FROM Note n LEFT JOIN FETCH n.tags WHERE n.owner = :owner")
    List<Note> findByOwnerWithTags(@Param("owner") User owner);
}

package com.bridgelabz.notes.repository;

import com.bridgelabz.notes.entity.Note;
import com.bridgelabz.notes.entity.NoteCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteCollaboratorRepository extends JpaRepository<NoteCollaborator, Integer> {

    Optional<NoteCollaborator> findByNoteAndCollaboratorId(Note note, int collaboratorId);

    Optional<NoteCollaborator> findByNoteNoteIdAndCollaboratorId(int noteId, int collaboratorId);

    List<NoteCollaborator> findByNote(Note note);

    List<NoteCollaborator> findByCollaboratorId(int collaboratorId);

    void deleteByNoteAndCollaboratorId(Note note, int collaboratorId);
}

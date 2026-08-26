package com.bridgelabz.notes.repository;

import com.bridgelabz.notes.entity.Note;
import com.bridgelabz.notes.entity.NoteCheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteCheckListRepository extends JpaRepository<NoteCheckList, Integer> {
    List<NoteCheckList> findByNote(Note note);
}

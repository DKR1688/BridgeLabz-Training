package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.NoteCheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteCheckListRepository extends JpaRepository<NoteCheckList, Integer> {

    List<NoteCheckList> findByNote_NoteIdAndIsDeletedFalse(int noteId);

    List<NoteCheckList> findByNote_NoteId(int noteId);

    Optional<NoteCheckList> findByIdAndNote_NoteId(int id, int noteId);

    Optional<NoteCheckList> findByIdAndNote_NoteIdAndIsDeletedFalse(int id, int noteId);
}

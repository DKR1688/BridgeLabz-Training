package com.bridgelabz.fundoonotes.config;

import com.bridgelabz.fundoonotes.dto.NoteImportRow;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.exception.InvalidNoteRowException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BatchConfig {

    public static final String IMPORT_NOTES_JOB = "importNotesJob";
    public static final String IMPORT_NOTES_STEP = "importNotesStep";

    @Bean
    public ItemProcessor<NoteImportRow, Note> noteImportProcessor() {
        return row -> {
            if (row.title() == null || row.title().trim().isEmpty()) {
                throw new InvalidNoteRowException(row.rowNumber(), "Title cannot be blank");
            }
            Note note = new Note();
            note.setTitle(row.title().trim());
            note.setContent(row.content() != null ? row.content() : "");
            note.setColor(row.color());
            note.setTypeOfNote(row.typeOfNote() != null && !row.typeOfNote().isBlank() ? row.typeOfNote() : "TEXT");
            note.setState(Note.NoteState.ACTIVE);
            note.setPinned(false);
            return note;
        };
    }

    @Bean
    public ItemWriter<Note> noteImportWriter(NoteRepository noteRepository) {
        return chunk -> {
            List<? extends Note> items = chunk.getItems();
            if (!items.isEmpty()) {
                noteRepository.saveAll(items);
            }
        };
    }
}

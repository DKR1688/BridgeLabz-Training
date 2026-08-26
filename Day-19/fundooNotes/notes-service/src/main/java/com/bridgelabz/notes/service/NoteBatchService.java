package com.bridgelabz.notes.service;

import com.bridgelabz.notes.dto.BatchJobResponse;
import com.bridgelabz.notes.dto.NoteImportRow;
import com.bridgelabz.notes.entity.Note;
import com.bridgelabz.notes.exception.InvalidNoteRowException;
import com.bridgelabz.notes.repository.NoteRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NoteBatchService {

    private static final Logger logger = LoggerFactory.getLogger(NoteBatchService.class);

    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final NoteRepository noteRepository;

    public NoteBatchService(
            JobLauncher jobLauncher,
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            NoteRepository noteRepository) {
        this.jobLauncher = jobLauncher;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.noteRepository = noteRepository;
    }

    public BatchJobResponse importNotes(MultipartFile file, int userId) {
        List<NoteImportRow> rows = new ArrayList<>();
        AtomicInteger skippedCount = new AtomicInteger(0);
        AtomicInteger importedCount = new AtomicInteger(0);

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = 0;
            for (Row row : sheet) {
                rowNum++;
                if (rowNum == 1) continue; // Skip header row

                Cell titleCell = row.getCell(0);
                Cell contentCell = row.getCell(1);

                String title = getCellValueAsString(titleCell);
                String content = getCellValueAsString(contentCell);

                if (title == null || title.trim().isEmpty()) {
                    logger.info("Skipped invalid row {}: Row {}: Title cannot be blank", rowNum, rowNum);
                    skippedCount.incrementAndGet();
                    continue;
                }

                rows.add(new NoteImportRow(title.trim(), content != null ? content.trim() : ""));
            }

            ItemReader<NoteImportRow> reader = new IteratorItemReader<>(rows);
            ItemProcessor<NoteImportRow, Note> processor = item -> {
                Note note = new Note();
                note.setTitle(item.getTitle());
                note.setContent(item.getContent());
                note.setOwnerId(userId);
                note.setState(Note.NoteState.ACTIVE);
                return note;
            };

            ItemWriter<Note> writer = chunk -> {
                for (Note note : chunk) {
                    noteRepository.save(note);
                    importedCount.incrementAndGet();
                }
            };

            Step step = new StepBuilder("importNotesStep", jobRepository)
                    .<NoteImportRow, Note>chunk(10, transactionManager)
                    .reader(reader)
                    .processor(processor)
                    .writer(writer)
                    .faultTolerant()
                    .skip(InvalidNoteRowException.class)
                    .skipLimit(100)
                    .build();

            Job job = new JobBuilder("importNotesJob-" + System.currentTimeMillis(), jobRepository)
                    .start(step)
                    .build();

            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addLong("userId", (long) userId)
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(job, params);
            return new BatchJobResponse(execution.getStatus().toString(), importedCount.get(), skippedCount.get());

        } catch (Exception e) {
            logger.error("Error during batch import: {}", e.getMessage(), e);
            throw new RuntimeException("Batch import failed: " + e.getMessage(), e);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}

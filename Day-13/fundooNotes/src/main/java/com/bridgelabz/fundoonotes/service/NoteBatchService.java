package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.BatchJobResponse;
import com.bridgelabz.fundoonotes.dto.NoteImportRow;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.InvalidNoteRowException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.listener.SkipListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NoteBatchService {

    private static final Logger logger = LoggerFactory.getLogger(NoteBatchService.class);

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final ItemProcessor<NoteImportRow, Note> noteImportProcessor;
    private final ItemWriter<Note> noteImportWriter;
    private final JobLauncher jobLauncher;

    public NoteBatchService(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            NoteRepository noteRepository,
            UserRepository userRepository,
            ItemProcessor<NoteImportRow, Note> noteImportProcessor,
            ItemWriter<Note> noteImportWriter,
            @Autowired(required = false) JobLauncher jobLauncher) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.noteImportProcessor = noteImportProcessor;
        this.noteImportWriter = noteImportWriter;
        this.jobLauncher = jobLauncher;
    }

    public List<NoteImportRow> parseExcelRows(InputStream inputStream) throws Exception {
        List<NoteImportRow> rows = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                return rows;
            }

            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();

            // Detect if first row is header
            int startRow = firstRowNum;
            if (firstRowNum <= lastRowNum) {
                Row firstRow = sheet.getRow(firstRowNum);
                if (firstRow != null) {
                    String firstCellText = formatter.formatCellValue(firstRow.getCell(0)).trim().toLowerCase();
                    if (firstCellText.contains("title") || firstCellText.contains("name")
                            || firstCellText.contains("note")) {
                        startRow = firstRowNum + 1;
                    }
                }
            }

            for (int r = startRow; r <= lastRowNum; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                String title = formatter.formatCellValue(row.getCell(0)).trim();
                String content = formatter.formatCellValue(row.getCell(1)).trim();
                String color = row.getCell(2) != null ? formatter.formatCellValue(row.getCell(2)).trim() : null;
                String typeOfNote = row.getCell(3) != null ? formatter.formatCellValue(row.getCell(3)).trim() : "TEXT";

                // Count row if not completely empty
                if (!title.isEmpty() || !content.isEmpty() || (color != null && !color.isEmpty())) {
                    rows.add(new NoteImportRow(r + 1, title, content, color, typeOfNote));
                } else {
                    // Empty title row to test skip counting
                    rows.add(new NoteImportRow(r + 1, "", content, color, typeOfNote));
                }
            }
        }
        return rows;
    }

    public BatchJobResponse importNotes(int userId, InputStream excelInputStream) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        try {
            List<NoteImportRow> rawRows = parseExcelRows(excelInputStream);
            long totalRead = rawRows.size();

            AtomicLong skipCounter = new AtomicLong(0);
            AtomicLong writeCounter = new AtomicLong(0);

            // Step with chunk processing and skip policy
            IteratorItemReader<NoteImportRow> reader = new IteratorItemReader<>(rawRows);

            ItemProcessor<NoteImportRow, Note> processor = row -> {
                Note note = noteImportProcessor.process(row);
                if (note != null) {
                    note.setOwner(owner);
                }
                return note;
            };

            ItemWriter<Note> countingWriter = chunk -> {
                noteImportWriter.write(chunk);
                writeCounter.addAndGet(chunk.size());
            };

            Step importNotesStep = new StepBuilder("importNotesStep", jobRepository)
                    .<NoteImportRow, Note>chunk(100, transactionManager)
                    .reader(reader)
                    .processor(processor)
                    .writer(countingWriter)
                    .faultTolerant()
                    .skip(InvalidNoteRowException.class)
                    .skipLimit(1000)
                    .listener(new SkipListener<NoteImportRow, Note>() {
                        @Override
                        public void onSkipInProcess(NoteImportRow item, Throwable t) {
                            skipCounter.incrementAndGet();
                            logger.info("Skipped invalid row {}: {}", item.rowNumber(), t.getMessage());
                        }
                    })
                    .build();

            Job job = new JobBuilder("importNotesJob-" + System.currentTimeMillis(), jobRepository)
                    .start(importNotesStep)
                    .build();

            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addLong("userId", (long) userId)
                    .toJobParameters();

            if (jobLauncher != null) {
                jobLauncher.run(job, params);
            } else {
                // Direct fallback execution if launcher is not injected
                for (NoteImportRow row : rawRows) {
                    try {
                        Note note = processor.process(row);
                        if (note != null) {
                            noteRepository.save(note);
                            writeCounter.incrementAndGet();
                        }
                    } catch (InvalidNoteRowException e) {
                        skipCounter.incrementAndGet();
                    }
                }
            }

            long readCount = totalRead;
            long writeCount = writeCounter.get();
            long skipCount = skipCounter.get();

            return new BatchJobResponse(
                    readCount,
                    writeCount,
                    skipCount,
                    "COMPLETED",
                    String.format("Successfully processed %d rows: %d imported, %d skipped", readCount, writeCount,
                            skipCount));

        } catch (Exception ex) {
            logger.error("Error executing Excel import job for userId={}", userId, ex);
            return new BatchJobResponse(0, 0, 0, "FAILED", "Import failed: " + ex.getMessage());
        }
    }
}

package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.Tag;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteExportService {

    private static final Logger logger = LoggerFactory.getLogger(NoteExportService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteExportService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public byte[] exportUserNotesToExcel(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Note> notes = noteRepository.findByOwnerWithTags(user);

        try (Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Fundoo Notes");

            // Header Font and Style
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setFontHeightInPoints((short) 11);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Columns
            String[] columns = {
                    "Note ID",
                    "Title",
                    "Description",
                    "State",
                    "Pinned",
                    "Color",
                    "Type of Note",
                    "Created At",
                    "Tags/Labels",
                    "Collaborators"
            };

            Row headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(24);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Data rows
            int rowIdx = 1;
            for (Note note : notes) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(note.getNoteId());
                row.createCell(1).setCellValue(note.getTitle() != null ? note.getTitle() : "");
                row.createCell(2).setCellValue(note.getContent() != null ? note.getContent() : "");
                row.createCell(3).setCellValue(note.getState() != null ? note.getState().name() : "ACTIVE");
                row.createCell(4).setCellValue(note.isPinned() ? "YES" : "NO");
                row.createCell(5).setCellValue(note.getColor() != null ? note.getColor() : "");
                row.createCell(6).setCellValue(note.getTypeOfNote() != null ? note.getTypeOfNote() : "TEXT");
                row.createCell(7)
                        .setCellValue(note.getCreatedAt() != null ? note.getCreatedAt().format(DATE_FORMATTER) : "");

                String tagsStr = "";
                if (note.getTags() != null && !note.getTags().isEmpty()) {
                    tagsStr = note.getTags().stream().map(Tag::getName).collect(Collectors.joining(", "));
                }
                row.createCell(8).setCellValue(tagsStr);

                String collaboratorsStr = "";
                if (note.getCollaborators() != null && !note.getCollaborators().isEmpty()) {
                    collaboratorsStr = note.getCollaborators().stream().map(User::getEmail)
                            .collect(Collectors.joining(", "));
                }
                row.createCell(9).setCellValue(collaboratorsStr);
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            logger.error("Failed to generate Excel export workbook for userId={}", userId, e);
            throw new RuntimeException("Could not export notes to Excel: " + e.getMessage(), e);
        }
    }
}

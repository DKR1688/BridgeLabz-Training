package com.bridgelabz.notes.service;

import com.bridgelabz.notes.dto.NoteResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class NoteExportService {

    private final NoteService noteService;

    public NoteExportService(NoteService noteService) {
        this.noteService = noteService;
    }

    public byte[] exportUserNotesToExcel(int userId) throws IOException {
        List<NoteResponse> notes = noteService.getAllNotes(userId);

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Notes");

            // Create Header Style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Create Headers
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Title", "Content", "State", "Color", "Pinned", "Tags", "Checklist Items", "Reminders", "Created At"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (NoteResponse note : notes) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(note.getNoteId());
                row.createCell(1).setCellValue(note.getTitle() != null ? note.getTitle() : "");
                row.createCell(2).setCellValue(note.getContent() != null ? note.getContent() : "");
                row.createCell(3).setCellValue(note.getState() != null ? note.getState() : "ACTIVE");
                row.createCell(4).setCellValue(note.getColor() != null ? note.getColor() : "");
                row.createCell(5).setCellValue(note.isPinned());
                row.createCell(6).setCellValue(note.getTags() != null ? String.join(", ", note.getTags()) : "");

                String checklistStr = "";
                if (note.getCheckLists() != null && !note.getCheckLists().isEmpty()) {
                    checklistStr = note.getCheckLists().stream()
                            .map(item -> (item.isDone() ? "[X] " : "[ ] ") + item.getItem())
                            .reduce((a, b) -> a + "; " + b)
                            .orElse("");
                }
                row.createCell(7).setCellValue(checklistStr);

                String reminderStr = "";
                if (note.getReminders() != null && !note.getReminders().isEmpty()) {
                    reminderStr = note.getReminders().stream()
                            .map(Object::toString)
                            .reduce((a, b) -> a + "; " + b)
                            .orElse("");
                }
                row.createCell(8).setCellValue(reminderStr);

                row.createCell(9).setCellValue(note.getCreatedAt() != null ? note.getCreatedAt().toString() : "");
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}

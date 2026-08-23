package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class SpringBatchExcelIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    private String userToken;
    private int userId;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        long ts = System.nanoTime();
        String email = "batch_user_" + ts + "@example.com";
        userToken = userService.register(email, "Password@123", "Batch User", "Batch", "User");
        userId = userRepository.findByEmail(email).get().getUserId();
    }

    private byte[] create50RowExcelFile(int validCount, int invalidCount) throws Exception {
        try (Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("NotesToImport");

            // Header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Title");
            header.createCell(1).setCellValue("Content");
            header.createCell(2).setCellValue("Color");
            header.createCell(3).setCellValue("TypeOfNote");

            int rowIdx = 1;
            // 45 Valid rows
            for (int i = 1; i <= validCount; i++) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue("Valid Note " + i);
                row.createCell(1).setCellValue("Bulk imported content for note #" + i);
                row.createCell(2).setCellValue("#FFAA" + (i % 100));
                row.createCell(3).setCellValue("TEXT");
            }

            // 5 Invalid rows (blank title)
            for (int i = 1; i <= invalidCount; i++) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(""); // Deliberately blank title!
                row.createCell(1).setCellValue("Invalid item with missing title #" + i);
                row.createCell(2).setCellValue("#FFFFFF");
                row.createCell(3).setCellValue("TEXT");
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    @Test
    @DisplayName("Use Case 11: Bulk-import 50 rows (45 valid, 5 invalid) via Spring Batch chunk processing")
    void testExcelImportWithValidationAndSkipCounting() throws Exception {
        byte[] excelBytes = create50RowExcelFile(45, 5);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "notes_50_rows.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                excelBytes);

        mockMvc.perform(multipart("/notes/import")
                .file(file)
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.readCount").value(50))
                .andExpect(jsonPath("$.writeCount").value(45))
                .andExpect(jsonPath("$.skipCount").value(5));

        User user = userRepository.findById(userId).get();
        long activeNoteCount = noteRepository.findByOwner(user).size();
        assertThat(activeNoteCount).isGreaterThanOrEqualTo(45);
    }

    @Test
    @DisplayName("Use Case 11: Export user's notes to valid, openable Excel .xlsx file with Apache POI")
    void testExcelExport() throws Exception {
        User user = userRepository.findById(userId).get();
        noteRepository.save(new Note("Export Test Note 1", "Content 1", user));
        noteRepository.save(new Note("Export Test Note 2", "Content 2", user));

        MvcResult result = mockMvc.perform(get("/notes/export")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(
                        header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"fundoo_notes.xlsx\""))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .andReturn();

        byte[] downloadedBytes = result.getResponse().getContentAsByteArray();
        assertThat(downloadedBytes).isNotEmpty();

        // Verify the downloaded file is a valid openable Excel workbook
        try (Workbook downloadedWorkbook = WorkbookFactory.create(new ByteArrayInputStream(downloadedBytes))) {
            Sheet sheet = downloadedWorkbook.getSheet("Fundoo Notes");
            assertThat(sheet).isNotNull();

            Row headerRow = sheet.getRow(0);
            assertThat(headerRow.getCell(0).getStringCellValue()).isEqualTo("Note ID");
            assertThat(headerRow.getCell(1).getStringCellValue()).isEqualTo("Title");
            assertThat(headerRow.getCell(2).getStringCellValue()).isEqualTo("Description");

            // At least 2 data rows present
            assertThat(sheet.getLastRowNum()).isGreaterThanOrEqualTo(2);
        }
    }
}

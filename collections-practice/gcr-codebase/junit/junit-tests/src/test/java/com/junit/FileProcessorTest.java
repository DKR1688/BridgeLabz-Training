package com.junit;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class FileProcessorTest {
	String dataFile="D:\\BridgeLabz-Training\\collections-practice\\gcr-codebase\\junit\\junit-tests\\src\\test\\java\\com\\junit\\data";
	String content="Hi, My name is Deepak.";
	@BeforeEach
    void setUp() {
        System.out.println("Test setup complete.");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(dataFile));
        System.out.println("Test cleanup done.");
    }

	
	@Test
	public void writeFileTest() throws IOException {
		FileProcessor.writeToFile(dataFile, content);
		System.out.println("Written content is- "+content);
		assertEquals(content, FileProcessor.readFromFile(dataFile));
	}
	
	@Test
	public void readFileTest() throws IOException{
		FileProcessor.writeToFile(dataFile, content);
		System.out.println("Read content is- "+FileProcessor.readFromFile(dataFile));
		assertEquals(content, FileProcessor.readFromFile(dataFile));
	}
	
	@Test
	public void fileExistAfterWritting() throws IOException {
		FileProcessor.writeToFile(dataFile, "And I am 21 years old.");
		boolean exists=Files.exists(Paths.get(dataFile));
		System.out.println("File exists- "+exists);
		assertTrue(exists);
	}
	
	@Test
	public void IOExceptionWhenFileDoesNotExist() {
		System.out.println("Exception when file does not exists..");
		assertThrows(IOException.class, () -> {
			FileProcessor.readFromFile("file.txt");});
	}
}

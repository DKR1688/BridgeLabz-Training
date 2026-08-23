package com.bridgelabz.fundoonotes.dto;

public record NoteImportRow(
                int rowNumber,
                String title,
                String content,
                String color,
                String typeOfNote) {
}

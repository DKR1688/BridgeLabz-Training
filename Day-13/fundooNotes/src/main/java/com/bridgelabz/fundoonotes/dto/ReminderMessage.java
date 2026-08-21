package com.bridgelabz.fundoonotes.dto;

import java.io.Serializable;

public record ReminderMessage(
                int noteId,
                int userId,
                String reminderTime,
                String noteTitle) implements Serializable {
}

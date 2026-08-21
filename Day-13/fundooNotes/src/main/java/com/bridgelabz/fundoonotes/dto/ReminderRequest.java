package com.bridgelabz.fundoonotes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReminderRequest(
                Integer noteId,
                String reminder,
                List<String> reminderList,
                LocalDateTime reminderTime) {
}

package com.bridgelabz.fundoonotes;

import com.bridgelabz.fundoonotes.aspect.ExecutionTimeAspect;
import com.bridgelabz.fundoonotes.aspect.ServiceExceptionLoggingAspect;
import com.bridgelabz.fundoonotes.dto.CheckListRequest;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.NoteNotFoundException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.CheckListService;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AopExecutionLoggingIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private CheckListService checkListService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    void setUp() {
        ExecutionTimeAspect.resetCount();
        ServiceExceptionLoggingAspect.resetCount();
    }

    @Test
    @DisplayName("Use Case 15: AOP @Around ExecutionTimeAspect intercepts service method calls across all services")
    void testExecutionTimeAspectInterceptsAllServiceMethods() {
        int initialCount = ExecutionTimeAspect.getExecutionCount();

        // Invoke UserService
        String email = "aop_user_" + System.nanoTime() + "@example.com";
        userService.register(email, "Password@123", "AOP User");
        User user = userRepository.findByEmail(email).get();
        int userId = user.getUserId();

        // Invoke NoteService
        Note note = noteService.createNote(userId, "AOP Note", "AOP Content");

        // Invoke LabelService
        labelService.createLabel(userId, "AOP Label");

        // Invoke CheckListService
        checkListService.addCheckListItem(note.getNoteId(), userId, new CheckListRequest("AOP Item", "PENDING", false));

        int finalCount = ExecutionTimeAspect.getExecutionCount();

        // Verifies aspect intercepted all service methods without any explicit code inside the services
        assertThat(finalCount).isGreaterThan(initialCount);
        assertThat(finalCount - initialCount).isGreaterThanOrEqualTo(4);
    }

    @Test
    @DisplayName("Use Case 15: AOP @AfterThrowing ServiceExceptionLoggingAspect logs exceptions thrown in services")
    void testAfterThrowingAspectLogsExceptions() {
        int initialExceptionCount = ServiceExceptionLoggingAspect.getExceptionLogCount();

        // Calling noteService with non-existent noteId will throw NoteNotFoundException
        assertThrows(NoteNotFoundException.class, () -> {
            noteService.archiveNote(999999, 1);
        });

        int finalExceptionCount = ServiceExceptionLoggingAspect.getExceptionLogCount();
        assertThat(finalExceptionCount).isGreaterThan(initialExceptionCount);
    }
}

import java.util.*;

public class ClassroomAttendanceTracker {
	public static void main(String[] args) {
        trackAttendance tracker=new trackAttendance();

        try {
            tracker.markAttendance("S1", "01");
            tracker.markAttendance("S1", "02");
            tracker.markAttendance("S1", "01");
            System.out.println();
        } catch (DuplicateAttendanceException e) {
            System.out.println("Error- " +e.getMessage());
            System.out.println();
        }

        tracker.displayAttendance("S1");
        tracker.removeAttendance("S1", "02");
        tracker.displayAttendance("S1");
        System.out.println();
    }
}

class DuplicateAttendanceException extends Exception {
	public DuplicateAttendanceException(String messege) {
		super(messege);
	}
}

class trackAttendance {
	Map<String, Set<String>> sessionAttendance;

	trackAttendance() {
		sessionAttendance=new HashMap<>();
	}

	//marking attendance
	public void markAttendance(String sessionId, String studentId) throws DuplicateAttendanceException {
		sessionAttendance.putIfAbsent(sessionId, new HashSet<>());
		Set<String> students =sessionAttendance.get(sessionId);

		if (students.contains(studentId)) {
			throw new DuplicateAttendanceException("Student "+studentId+ " already marked in session "+sessionId);
		}
		students.add(studentId);
		System.out.println("Attendance marked- "+studentId +" in session "+sessionId);
	}

	public void removeAttendance(String sessionId, String studentId) {
		if (sessionAttendance.containsKey(sessionId)) {
			sessionAttendance.get(sessionId).remove(studentId);
			System.out.println("Attendance removed- "+studentId + " from session "+sessionId);
		} else {
			System.out.println("Session not found- "+sessionId);
		}
	}

	public void displayAttendance(String sessionId) {
		if (sessionAttendance.containsKey(sessionId)) {
			System.out.println("Attendance for session " +sessionId + "- "+sessionAttendance.get(sessionId));
		} else {
			System.out.println("No attendance found for session "+sessionId);
		}
	}
}

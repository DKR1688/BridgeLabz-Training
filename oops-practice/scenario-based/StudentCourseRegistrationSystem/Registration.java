package StudentCourseRegistrationSystem;

public class Registration implements RegistrationService {
	public void registerStudent(Student student) {
        System.out.println("Registered: " + student.getName() + " (ID: " + student.getId() + ")");
    }

    public void enrollCourse(Student student, String course) {
        try {
            student.addCourse(course);
        } catch (CourseLimitExceededException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void dropCourse(Student student, String course) {
        student.dropCourse(course);
    }

    public void assignGrade(Student student, String grade) {
        student.setGrade(grade);
        System.out.println("Grade " + grade + " assigned to " + student.getName());
    }
}

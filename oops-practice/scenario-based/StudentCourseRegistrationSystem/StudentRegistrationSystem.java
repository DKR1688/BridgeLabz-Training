package StudentCourseRegistrationSystem;

public class StudentRegistrationSystem {
	public static void main(String[] args) {
        RegistrationService service = new Registration();

        Student s1 = new Student("Deepak", 101);
        service.registerStudent(s1);

        service.enrollCourse(s1, "Math");
        service.enrollCourse(s1, "Physics");
        service.enrollCourse(s1, "Chemistry");
        service.enrollCourse(s1, "CS");

        s1.viewCourses();

        service.dropCourse(s1, "CS");
        s1.viewCourses();

        service.assignGrade(s1, "A");
        s1.viewGrade();
    }
}

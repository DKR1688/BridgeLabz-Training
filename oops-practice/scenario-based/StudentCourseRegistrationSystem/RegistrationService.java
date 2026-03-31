package StudentCourseRegistrationSystem;

public interface RegistrationService {
	public void registerStudent(Student student);
    public void assignGrade(Student student, String grade);
    public void enrollCourse(Student student, String course);
    public void dropCourse(Student student, String course);
}

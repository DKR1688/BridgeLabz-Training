package StudentCourseRegistrationSystem;

public class Student extends Person {
	private String[] courses;
    private int courseCount;
    private String grade;
    private static int maxCourses =5;

    public Student(String name, int id) {
        super(name, id);
        courses =new String[maxCourses];
        courseCount =0;
    }
    //method to give grade to student
    public void setGrade(String grade) { 
    	this.grade = grade; 
    }
    public String getGrade() { 
    	return grade; 
    }

    //method to manage student features
    public void addCourse(String course) throws CourseLimitExceededException {
        if (courseCount >=maxCourses) {
            throw new CourseLimitExceededException("Course limit exceeded.");
        }
        courses[courseCount++] =course;
        System.out.println(getName() + " enrolled in " + course);
    }
    
    //method to drop course if it is exist
    public void dropCourse(String course) {
        for (int i=0; i<courseCount; i++) {
            if (courses[i].equals(course)) {
                courses[i] = courses[courseCount-1];
                courses[courseCount-1] =null;
                courseCount--;
                
                System.out.println(getName() + " dropped.");
                return;
            }
        }
        System.out.println("Course not found.");
    }

    public void viewCourses() {
        System.out.println("Available courses are- ");
        for (int i=0; i<courseCount; i++) {
            System.out.println(courses[i]);
        }
    }

    public void viewGrade() {
        System.out.println(getName() +"'s grade is- " +grade);
    }
}

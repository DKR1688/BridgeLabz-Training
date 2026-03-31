
public class EducationalCourse {
	public static void main(String[] args) {
		PaidOnlineCourse poc = new PaidOnlineCourse("Java OOP", 6, "Udemy", true, 3000, 20);
		poc.displayCourseDetails();
	}
}

//Base class
class Course {
	String courseName;
	int duration;

	public Course(String courseName, int duration) {
		this.courseName = courseName;
		this.duration = duration;
	}
}

class OnlineCourse extends Course {
	String platform;
	boolean isRecorded;

	public OnlineCourse(String courseName, int duration, String platform, boolean isRecorded) {
		super(courseName, duration);
		this.platform = platform;
		this.isRecorded = isRecorded;
	}
}

class PaidOnlineCourse extends OnlineCourse {
	double fee;
	double discount;

	public PaidOnlineCourse(String courseName, int duration, String platform, boolean isRecorded, double fee,
			double discount) {
		super(courseName, duration, platform, isRecorded);
		this.fee = fee;
		this.discount = discount;
	}

	public void displayCourseDetails() {
		System.out.println("Course is- " + courseName + ",\n Duration is- " + duration + " weeks");
		System.out.println("Platform is- " + platform + ",\n Recorded is- " + isRecorded);
		System.out.println("Fee is- " + fee + ",\n Discount is- " + discount + "%");
	}
}

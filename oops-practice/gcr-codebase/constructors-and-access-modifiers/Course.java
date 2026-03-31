public class Course {
	
    //instance variables
    String courseName;
    String duration;
    int fee;

    //class variable
    static String instituteName ="XYZ Institute";

    Course(String courseName, String duration, int fee) {
        this.courseName =courseName;
        this.duration =duration;
        this.fee =fee;
    }

    //instance method to display course details
    void displayCourseDetails() {
        System.out.println("Course name is- " +courseName+"\n Duration is- " +duration+
                           "\n Fee is- " +fee+"\n Institute is- " +instituteName);
        System.out.println();
    }

    //class method to update institute name
    static void updateInstituteName(String newName) {
        instituteName =newName;
        System.out.println("Updated institute name is- " +instituteName);
        System.out.println();
    }
    
    public static void main(String[] args) {
        Course course1 =new Course("Java Programming", "3 Months",5000);
        course1.displayCourseDetails();
        
        Course course2 =new Course("Web Development", "6 Months", 2000);
        course2.displayCourseDetails();

        //Updated institute name using class method
        Course.updateInstituteName("GLA University");
    }
}
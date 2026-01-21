import java.util.*;
public class UniversityCourseManagementSystem {
	public static void main(String[] args) {
		ExamCourse math = new ExamCourse("Mathematics");
        AssignmentCourse cs = new AssignmentCourse("Computer Science");
        ResearchCourse physics = new ResearchCourse("Physics Research");

        //here we wrapping courses in generic class
        Course<ExamCourse> exam =new Course<>(math);
        Course<AssignmentCourse> assignment = new Course<>(cs);
        Course<ResearchCourse> research = new Course<>(physics);

        exam.showEvaluation();
        assignment.showEvaluation();
        research.showEvaluation();
        System.out.println();

        //this is use of wild cards
        Department depart =new Department("CS");
        
        List<CourseType> list =Arrays.asList(math, cs, physics);
        depart.showCourse(list);
	}
}

//here we create abstract class to different type of course
abstract class CourseType{
	String name;
	
	CourseType(String name){
		this.name =name;
	}
	
	public String getName() {
		return name;
	}
	
	abstract void evaluate();
}

class ExamCourse extends CourseType{
	ExamCourse(String courseName) {
        super(courseName);
    }

    @Override
    public void evaluate() {
        System.out.println(getName() +" is evaluated by final exams.");
    }
}

class AssignmentCourse extends CourseType {
    AssignmentCourse(String courseName) {
        super(courseName);
    }

    @Override
    public void evaluate() {
        System.out.println(getName() +" is evaluated by assignments.");
    }
}

class ResearchCourse extends CourseType {
    ResearchCourse(String courseName) {
        super(courseName);
    }

    @Override
    public void evaluate() {
        System.out.println(getName()+" is evaluated by research.");
    }
}

//here we are implementing generic class to manage courses
class Course<T extends CourseType>{
	T type;
	
	Course(T type){
		this.type =type;
	}
	
	public T getType() {
		return type;
	}
	
	public void showEvaluation() {
		type.evaluate();
	}
}

//here we created department class to use wild cards to handle all courses
class Department{
	String departName;
	
	Department(String departName){
		this.departName =departName;
	}
	
	public void showCourse(List<? extends CourseType> courses) {
		System.out.println("Deparment name is-"+departName);
		for(CourseType c: courses) {
			c.evaluate();
		}
	}
}
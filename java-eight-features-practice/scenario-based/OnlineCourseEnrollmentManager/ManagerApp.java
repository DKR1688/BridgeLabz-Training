package OnlineCourseEnrollmentManager;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
public class ManagerApp {
    public static void main(String[] args) {
    	List<Enrollment> list=Arrays.asList(new Enrollment("Deepak", "Java", "Programming", LocalDate.of(2024, 5, 10)), 
    										new Enrollment("Abhay", "Data Science", "Analytics", LocalDate.of(2024, 6, 12)), 
    										new Enrollment("Abhishek", "Java", "Programming", LocalDate.of(2024, 7, 15)), 
    										new Enrollment("Daman", "UI Design", "Design", LocalDate.of(2024, 8, 20)));

        System.out.println("Filter by course- ");
        List<Enrollment> filterByCourse =list.stream().filter(e -> e.getCourseName().equalsIgnoreCase("Java Basics")).collect(Collectors.toList()); 
        filterByCourse.stream().forEach(System.out::println);

        System.out.println("\nFilter by category- ");
        List<Enrollment> filterByCategory =list.stream().filter(e -> e.getCourseCategory().equalsIgnoreCase("Programming")).collect(Collectors.toList()); 
        filterByCategory.stream().forEach(System.out::println);

        System.out.println("\nGroup by course- ");
        Map<String, List<Enrollment>> groupByCourse= list.stream().collect(Collectors.groupingBy(Enrollment::getCourseName));
        groupByCourse.forEach((course, enrollments) -> System.out.println(course+" - "+enrollments));
        
        
        System.out.println("\nCount by category- ");
        Map<String, Long> countByCategory = list.stream().collect(Collectors.groupingBy(Enrollment::getCourseCategory, Collectors.counting()));
        countByCategory.forEach((cat, count) -> System.out.println(cat + " - " + count));

        System.out.println("\nSorted by enrollment date- ");
        List<Enrollment> sortedByDate = list.stream().sorted(Comparator.comparing(Enrollment::getEnrollmentDate)).collect(Collectors.toList());
        sortedByDate.forEach(System.out::println);
    }
}
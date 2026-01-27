package EduResults;
import java.util.*;
public class RankSheetGenerator {
	public static void main(String[] args) {
		List<Student> s1 =Arrays.asList(new Student("Deepak", 95, "Deeg"),
	            								new Student("Abhay", 90, "Deeg"));
		List<Student> s2 =Arrays.asList(new Student("Ajay", 98, "Bharatpur"),
												new Student("Karan", 92, "Bharatpur"));
		
		List<Student> students=new ArrayList<>();
		students.addAll(s1);
		students.addAll(s2);

		//sort all these lists into a final state-wise rank list
		MergeResults.mergeSort(students);
		System.out.println("All ranl list are- ");
		int rank=1;
		for(Student s: students) {
			System.out.println("Rank "+rank++ +" is- "+s);
		}
	}
}

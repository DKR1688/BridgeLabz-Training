package EduResults;
import java.util.*;
public class MergeResults {
	public static void mergeSort(List<Student> students) {
		if(students.size()<=1) {
			return;
		}
		int mid=students.size()/2;
		List<Student> left = new ArrayList<>(students.subList(0, mid));
        List<Student> right = new ArrayList<>(students.subList(mid, students.size()));
        
        mergeSort(left);
        mergeSort(right);
        
        merge(students, left, right);
	}
	
	static void merge(List<Student> students, List<Student> left, List<Student> right) {
        int i=0;
        int j=0;
        int k=0;

        while (i<left.size() && j<right.size()) {
            //here we will sort by score
            if (left.get(i).score >=right.get(j).score) {
                students.set(k++, left.get(i++));
            } else {
                students.set(k++, right.get(j++));
            }
        }

        //Copying remaining elements
        while (i<left.size()) {
            students.set(k++, left.get(i++));
        }
        while (j<right.size()) {
            students.set(k++, right.get(j++));
        }
    }
}

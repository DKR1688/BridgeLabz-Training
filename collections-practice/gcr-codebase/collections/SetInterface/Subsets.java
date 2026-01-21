package SetInterface;
import java.util.*;
public class Subsets {
	public static void main(String[] args) {
		Set<Integer> set1=new HashSet<>();
		Set<Integer> set2=new HashSet<>();
		set1.add(2);
		set1.add(3);
		set2.add(1);
		set2.add(2);
		set2.add(3);
		set2.add(4);
		
		boolean isSubset =false;
		if(set2.containsAll(set1)) {
			isSubset=true;
		}
		System.out.println("Are set1 is sub set of set2- "+isSubset);
	}
}

package SetInterface;
import java.util.*;
public class CheckSetsEqual {
	public static void main(String[] args) {
		Set<Integer> set1=new HashSet<>();
		Set<Integer> set2=new HashSet<>();
		set1.add(1);
		set1.add(2);
		set1.add(3);
		set2.add(3);
		set2.add(2);
		set2.add(1);
		
		System.out.println("Set 1 is- "+set1+"\n Set 2 is- "+set2);
		boolean isEqual =set1.equals(set2);
		System.out.println("Are both sets equal- "+isEqual);
	}
}

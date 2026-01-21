package SetInterface;
import java.util.*;
public class SetToSortedList {
	public static void main(String[] args) {
		Set<Integer> set=new HashSet<>();
		set.add(5);
		set.add(3);
		set.add(9);
		set.add(1);
		
		List<Integer> list=new ArrayList<>();
		list.addAll(set);
		Collections.sort(list);
		
		System.out.println("After converting set to sorted list is- "+list);
	}
}

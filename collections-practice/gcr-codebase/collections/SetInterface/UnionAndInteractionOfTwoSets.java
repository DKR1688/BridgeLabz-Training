package SetInterface;
import java.util.*;
public class UnionAndInteractionOfTwoSets {
	public static void main(String[] args) {
		Set<Integer> set1=new HashSet<>();
		Set<Integer> set2=new HashSet<>();
		set1.add(1);
		set1.add(2);
		set1.add(3);
		set2.add(3);
		set2.add(4);
		set2.add(5);
		
		Set<Integer> union=new HashSet<>();
		union.addAll(set1);
		union.addAll(set2);
		System.out.println("Union of two sets- "+union);
		System.out.println();
		
		Set<Integer> interaction=new HashSet<>();
		interaction.addAll(set1);
		interaction.retainAll(set2);
		System.out.println("Interaction of two sets- "+interaction);
	}
}

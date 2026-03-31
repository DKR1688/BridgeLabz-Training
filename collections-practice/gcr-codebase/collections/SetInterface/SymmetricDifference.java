package SetInterface;
import java.util.*;
public class SymmetricDifference {
	public static void main(String[] args) {
		Set<Integer> set1=new HashSet<>();
		Set<Integer> set2=new HashSet<>();
		set1.add(1);
		set1.add(2);
		set1.add(3);
		set2.add(3);
		set2.add(4);
		set2.add(5);
		
		Set<Integer> interaction =new HashSet<>();
		interaction.addAll(set1);
		interaction.retainAll(set2);
		
		Set<Integer> diff=new HashSet<>();
		diff.addAll(set1);
		diff.addAll(set2);
		diff.removeAll(interaction);
		
		System.out.println("Symmetric difference of two sets- "+diff);
	}
}

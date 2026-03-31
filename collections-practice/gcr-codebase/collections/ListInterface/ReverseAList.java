package ListInterface;
import java.util.*;
public class ReverseAList {
	public static void main(String[] args) {
		//Reversing array list
		ArrayList<Integer> list =new ArrayList<>();
		for(int i=0; i<7; i++) {
			list.add(i);
		}
		System.out.println("Befor reverse- "+list);
		for(int i=0; i<list.size()/2; i++) {
			int temp =list.get(i);
			list.set(i, list.get(list.size()-1-i));
			list.set(list.size()-1-i, temp);
		}
		System.out.println("Reversed list are- "+list);
		System.out.println();
		
		
		//Reversing linked list
		LinkedList<Integer> linked =new LinkedList<>();
		for(int i=0; i<9; i++) {
			linked.add(i);
		}
		System.out.println("Before linked list- "+linked);
		for(int i=0; i<linked.size()/2; i++) {
			int temp =linked.get(i);
			linked.set(i, linked.get(linked.size()-1-i));
			linked.set(linked.size()-1-i, temp);
		}
		System.out.println("Reversed linked list are- "+linked);
	}
}

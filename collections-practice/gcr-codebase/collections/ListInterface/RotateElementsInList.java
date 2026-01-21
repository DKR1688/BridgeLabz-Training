package ListInterface;
import java.util.*;
public class RotateElementsInList {
	public static void main(String[] args) {
		ArrayList<Integer> list=new ArrayList<>();
		list.add(10);
		list.add(20);
		list.add(30);
		list.add(40);
		list.add(50);
		
		//rotating list
		System.out.println("List before rotating- "+list);
		int position=2;
		List<Integer> list2 =new ArrayList<>();
		for(int i=position; i<list.size(); i++) {
			list2.add(list.get(i));
		}
		for(int i=0; i<position; i++) {
			list2.add(list.get(i));
		}
		System.out.println("List after rotating- "+list2);
	}
}

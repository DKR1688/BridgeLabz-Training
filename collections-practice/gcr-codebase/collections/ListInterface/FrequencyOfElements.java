package ListInterface;
import java.util.*;
public class FrequencyOfElements {
	public static void main(String[] args) {
		ArrayList<String> list=new ArrayList<>();
		list.add("Deepak");
		list.add("Abhay");
		list.add("Hello");
		list.add("Deepak");
		list.add("Abhay");
		
		Map<String, Integer> map=new HashMap<>();
		for(String string: list) {
			if(map.containsKey(string)) {
				map.put(string, map.get(string)+1);
			}else {
				map.put(string, 1);
			}
		}
		System.out.println("Frequency of strings are- "+map);
	}
}

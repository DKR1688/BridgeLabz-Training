package MapInterface;
import java.util.*;
public class InvertAMap {
	public static void main(String[] args) {
        Map<String, Integer> input = new HashMap<>();
        input.put("A", 1);
        input.put("B", 2);
        input.put("C", 1);

        //inverted map
        Map<Integer, List<String>> inverted = new HashMap<>();
        for (Map.Entry<String, Integer> entry :input.entrySet()) {
        	//here computeIfAbsent create a new list if value is absent
            inverted.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(entry.getKey());
        }
        System.out.println(inverted);
    }
}

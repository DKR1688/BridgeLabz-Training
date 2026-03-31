package ListInterface;
import java.util.*;
public class RemoveDuplicates {
	public static void main(String[] args) {
        List<Integer> list =new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(4);
        
        Set<Integer> seen =new HashSet<>();
        List<Integer> ans =new ArrayList<>();

        System.out.println("Before removing duplicates- "+list);
        //iterate and preserve order
        for (Integer number :list) {
            if (!seen.contains(number)) {
                seen.add(number);
                ans.add(number);
            }
        }
        System.out.println("After removing duplicates- " +ans);
    }
}

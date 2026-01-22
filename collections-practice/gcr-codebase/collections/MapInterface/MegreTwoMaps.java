package MapInterface;
import java.util.*;
public class MegreTwoMaps {
	public static void main(String[] args) {
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);
        map2.put("B", 3);
        map2.put("C", 4);

        Map<String, Integer> ans= new HashMap<>(map1);
        for (Map.Entry<String, Integer> entry :map2.entrySet()) {
            ans.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }

        System.out.println(ans);
    }
}

import java.util.HashSet;
import java.util.TreeSet;

public class ComparingDataStructuresForSearching {
public static void main(String[] args) {
		int n =1000000;
        int target = n-1;

        int[] array = new int[n];
        HashSet<Integer> hashSet = new HashSet<>();
        TreeSet<Integer> treeSet = new TreeSet<>();

        for (int i=0; i<n; i++) {
            array[i]=i;
            hashSet.add(i);
            treeSet.add(i);
        }

        long start;
        long end;

        //Array Search
        start =System.nanoTime();
        for (int i=0; i<n; i++) {
            if (array[i] == target)
                break;
        }
        end = System.nanoTime();
        System.out.println("Array search time is- " + (end - start) / 1_000_000 + " ms");

        //HashSet Search
        start = System.nanoTime();
        hashSet.contains(target);
        end = System.nanoTime();
        System.out.println("HashSet search time is- " + (end - start) / 1_000_000 + " ms");

        //TreeSet Search
        start = System.nanoTime();
        treeSet.contains(target);
        end = System.nanoTime();
        System.out.println("TreeSet search time is- " + (end - start) / 1_000_000 + " ms");
	}
}

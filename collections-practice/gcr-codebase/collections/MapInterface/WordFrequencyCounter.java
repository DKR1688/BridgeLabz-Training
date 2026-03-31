package MapInterface;
import java.util.*;
public class WordFrequencyCounter {
	public static void main(String[] args){
		String text="Hello world, hello Java!";
        text =text.toLowerCase().replaceAll("[^a-z]", " ");

        String[] words =text.split("\\s+");
        Map<String, Integer> wordCount = new HashMap<>();
        for (String w : words) {
            if (!w.isEmpty()) {
                wordCount.put(w, wordCount.getOrDefault(w, 0) + 1);
            }
        }
        System.out.println(wordCount);
    }
}

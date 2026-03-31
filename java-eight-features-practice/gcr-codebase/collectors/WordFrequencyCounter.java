import java.util.*;
import java.util.stream.Collectors;
public class WordFrequencyCounter {
    public static void main(String[] args) {
        String paragraph ="Java is easy and Java is powerful and Collectors help in Java streams.";

        List<String> words =Arrays.asList(paragraph.toLowerCase().replaceAll("[^a-z ]", "").split("\\s+"));
        Map<String, Integer> wordCount = words.stream()
            .collect(Collectors.toMap(word -> word, w -> 1, Integer::sum));

        wordCount.forEach((word, count) -> System.out.println(word+" - "+count));
    }
}
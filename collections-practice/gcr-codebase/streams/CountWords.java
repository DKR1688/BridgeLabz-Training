import java.io.*;
import java.util.*;
public class CountWords {
    public static void main(String[] args) {
        String filePath ="D:\\BridgeLabz-Training/collections-practice/gcr-codebase/streams/sourceFile.txt";

        Map<String, Integer> countMap=new HashMap<>();
        try (BufferedReader br=new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line =br.readLine())!=null) {
                String[] words = line.split("\\W+");

                for (String word:words) {
                    if (!word.isEmpty()) {
                        word =word.toLowerCase();

                        //updating count in HashMap
                        countMap.put(word, countMap.getOrDefault(word, 0) + 1);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file- "+e.getMessage());
            return;
        }

        //Sorting words by frequency
        List<Map.Entry<String, Integer>> sortedList =new ArrayList<>(countMap.entrySet());
        
        sortedList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        //total word count and top 5 frequent words
        int totalWords =countMap.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total words in file- "+totalWords);

        System.out.println("\nTop 5 most frequent words- ");
        for (int i=0; i<Math.min(5, sortedList.size()); i++) {
            Map.Entry<String, Integer> entry=sortedList.get(i);
            System.out.println((i+1)+ ". "+entry.getKey()+" -> "+entry.getValue()+" times");
        }
    }
}
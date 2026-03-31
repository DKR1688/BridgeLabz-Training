package FileReader;

import java.io.*;
public class OccurenceOfWordInFile{
    public static void main(String[] args) {
        //Replacing with the path to your file
        String filePath ="fileName.txt";  
        String targetWord = "java";

        int count =countWordOccurrences(filePath, targetWord);
        System.out.println("The word " +targetWord+" occurs "+count +" times in the file.");
    }

    //method to count occurrences of a word in a file
    public static int countWordOccurrences(String filePath, String targetWord) {
        int count=0;

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferReader =new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferReader.readLine()) !=null) {
                //Splitting line into words using whitespace
                String[] words =line.split(" ");

                for (String word :words) {
                    if (word.equalsIgnoreCase(targetWord)) {
                        count++;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}
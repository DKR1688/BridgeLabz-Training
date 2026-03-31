
import java.io.*;
import java.util.*;
public class CompareFileAndInputStreamReader {
    public static void main(String[] args) {
        String filePath ="largefileName.txt";

        //Read a large text file (100MB) using FileReader and InputStreamReader.
        long start =System.currentTimeMillis();
        int wordCountFR = countWordsUsingFileReader(filePath);
        long end = System.currentTimeMillis();
        System.out.println("FileReader word count is- " +wordCountFR);
        System.out.println("FileReader time- " +(end-start) + " ms");

        start =System.currentTimeMillis();
        int wordCountISR =countWordsUsingInputStreamReader(filePath);
        end = System.currentTimeMillis();
        System.out.println("InputStreamReader word count- " + wordCountISR);
        System.out.println("InputStreamReader time- " + (end-start) +" ms");
    }

    //Count the number of words by splitting the text on whitespace characters.
    public static int countWordsUsingFileReader(String filePath) {
        int wordCount=0;
        try (FileReader fileReader= new FileReader(filePath);
             BufferedReader bufferReader =new BufferedReader(fileReader)) {
            String line;
            while ((line =bufferReader.readLine()) != null) {
                StringTokenizer stringToken =new StringTokenizer(line);
                wordCount += stringToken.countTokens();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordCount;
    }

    public static int countWordsUsingInputStreamReader(String filePath) {
        int wordCount = 0;
        try (InputStreamReader inputReader = new InputStreamReader(new FileInputStream(filePath));
             BufferedReader bufferReader = new BufferedReader(inputReader)) {
            String line;
            while ((line = bufferReader.readLine()) != null) {
                StringTokenizer stringToken =new StringTokenizer(line);
                wordCount += stringToken.countTokens();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordCount;
    }
}
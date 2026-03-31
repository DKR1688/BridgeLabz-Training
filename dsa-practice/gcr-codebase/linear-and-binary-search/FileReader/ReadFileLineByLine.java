package FileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class ReadFileLineByLine{
    public static void main(String[] args) {
        //we will change the file path to file location
        String filePath ="fileName.txt";

        try {
            //Creating file reader
            FileReader fileReader =new FileReader(filePath);

            //Wrap FileReader in BufferedReader
            BufferedReader bufferReader =new BufferedReader(fileReader);
            String line;

            //Reading file line by line
            while ((line =bufferReader.readLine()) != null) {
                System.out.println(line);
            }

            //Close the file after reading all the lines.
            fileReader.close();
            bufferReader.close();

        } catch (IOException e) {
            System.out.println("Error occurred while reading the file.");
            e.printStackTrace();
        }
    }
}
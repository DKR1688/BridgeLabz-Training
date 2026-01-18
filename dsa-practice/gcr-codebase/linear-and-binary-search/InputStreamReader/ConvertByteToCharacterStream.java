package InputStreamReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
public class ConvertByteToCharacterStream{
    public static void main(String[] args) {
        //changing the file path to your file location
        String filePath ="fileName.txt";

        try {
        	//Create a FileInputStream object to read the binary data from the file.
            FileInputStream fileStream =new FileInputStream(filePath);

            //Wrap the FileInputStream in an InputStreamReader to convert the byte stream into a character stream.
            InputStreamReader inputStream =new InputStreamReader(fileStream, "UTF-8");

            //Use a BufferedReader to read characters efficiently from the InputStreamReader.
            BufferedReader bufferReader =new BufferedReader(inputStream);
            String line;

            //Read the file line by line and print the characters to the console.
            while ((line = bufferReader.readLine()) != null) {
                System.out.println(line);
            }

            //Handle any encoding exceptions as needed.
            bufferReader.close();
            inputStream.close();
            fileStream.close();

        } catch (IOException e) {
            System.out.println("Error reading file- " +e.getMessage());
        }
    }
}

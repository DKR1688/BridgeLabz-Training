package InputStreamReader;

import java.io.*;
public class UserInputToFile {
    public static void main(String[] args) {
        String fileName ="fileName.txt";

        try (//Create an InputStreamReader to read from System.in (the console).
            InputStreamReader inputReader =new InputStreamReader(System.in);
            BufferedReader bufferReader =new BufferedReader(inputReader);

            //Create FileWriter to write to file
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);) {
        	
        	//Repeat the process until the user enters "exit" to stop inputting.
            System.out.println("Enter text (type 'exit' to stop):");

            String line;
            while ((line = bufferReader.readLine()) !=null) {
                if (line.equalsIgnoreCase("exit")) {
                    break; // stop when user types 'exit'
                }
                //Write each line to file
                bufferWriter.write(line);
                bufferWriter.newLine();
            }
            System.out.println("User input has been written to " +fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
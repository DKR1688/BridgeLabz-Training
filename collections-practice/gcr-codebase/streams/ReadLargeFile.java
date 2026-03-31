import java.io.*;
public class ReadLargeFile {
	public static void main(String[] args) {
		String fileName ="/BridgeLabz-Training/collections-practice/gcr-codebase/streams/file";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber=1;
            System.out.println("Reading file line by line...");

            while ((line = br.readLine()) !=null) {
                if (line.toLowerCase().contains("error")) {
                    System.out.println("Line " + lineNumber + "- "+line);
                }
                lineNumber++;
            }

            System.out.println("Finished reading file.");

        } catch (IOException e) {
            System.out.println("Error reading file- "+e.getMessage());
        }
    }
}

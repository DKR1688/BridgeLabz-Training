import java.io.*;
public class TryWithResources {
	public static void main(String[] args) {
		//Using try-with-resources to ensure the file is automatically closed after reading
        try (BufferedReader br=new BufferedReader(new FileReader("D:\\BridgeLabz-Training\\collections-practice\\gcr-codebase\\exceptions\\data"))) {
            String firstLine =br.readLine();
            
            if (firstLine!=null) {
                System.out.println(firstLine);
            } else {
                System.out.println("File is empty.");
            }
            
        } catch (IOException e) {
            System.out.println("Error in reading file.");
        }
	}
}

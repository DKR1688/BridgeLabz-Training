import java.io.*;
public class CheckedException {
	public static void main(String[] args) {
		try {
			BufferedReader reader =new BufferedReader(new FileReader("D:\\BridgeLabz-Training\\collections-practice\\gcr-codebase\\exceptions\\data"));
			
			String line;
			while((line=reader.readLine())!=null) {
				System.out.println(line);
			}
			reader.close();
		} catch(IOException e) {
			System.out.println("File not found.");
		}
	}
}

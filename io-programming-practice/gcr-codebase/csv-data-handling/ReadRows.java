import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadRows {
	public static void main(String[] args) {
		String path="D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\employee.csv";
		String line;
		int c=0;
		 try (BufferedReader br = new BufferedReader(new FileReader(path))) {

	            br.readLine();
                
	            while ((line = br.readLine()) != null) {
	            	c++;	  
	            	}

	        } catch (IOException e) {
	            System.out.println("Error reading CSV file");
	            e.printStackTrace();
	        }
		 
System.out.println("There are "+ c +" lines");	    }
	}

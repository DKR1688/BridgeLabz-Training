import java.io.*;
public class ReadUserInputFromConsole {
	public static void main(String[] args) {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter your name- ");
            String name =br.readLine();

            System.out.print("Enter your age- ");
            String age =br.readLine();

            //fileWriter to save data into a file
            FileWriter fw=new FileWriter("UserInfo.txt");

            fw.write("User Information- \n");
            fw.write("Name- " +name+ "\n");
            fw.write("Age- " + age);
            fw.close();

            System.out.println("Information saved successfully in UserInfo.txt");

        } catch (IOException e) {
            System.out.println("Error occurred- "+e.getMessage());
        }
    }
}

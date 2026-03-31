import java.io.*;

public class FileHandler {
    public static void main(String[] args) {
        String sourceFile ="/BridgeLabz-Training/collections-practice/gcr-codebase/streams/sourceFile.txt";
        String destinationFile ="/BridgeLabz-Training/collections-practice/gcr-codebase/streams/destinationFile.txt";

        FileInputStream fis=null;
        FileOutputStream fos=null;
        try {
            //source file for reading
            fis=new FileInputStream(sourceFile);

            //destination file for writing
            fos=new FileOutputStream(destinationFile);

            int data;
            //we read byte by byte and write to destination
            while ((data = fis.read())!=-1) {
                fos.write(data);
            }
            System.out.println("File copied successfully.");

        } catch (IOException e) {
            System.out.println("Error- " +e.getMessage());
        } finally {
            try {
                if (fis!=null) {
                	fis.close();
                }
                if (fos!=null) {
                	fos.close();
                }
            } catch (IOException e) {
                System.out.println("Error while closing files is- "+e.getMessage());
            }
        }
    }
}
import java.io.*;
import java.util.Arrays;

public class ByteArrayStream{
    public static void main(String[] args) {
    	String sourceFile ="/BridgeLabz-Training/collections-practice/gcr-codebase/streams/sourceFile.txt";
        String destinationFile ="/BridgeLabz-Training/collections-practice/gcr-codebase/streams/destinationFile.txt";
       
        try {
            //reading image into byte array using ByteArrayOutputStream
            FileInputStream fis =new FileInputStream(sourceFile);
            ByteArrayOutputStream baos =new ByteArrayOutputStream();

            byte[] buffer=new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer))!=-1) {
                baos.write(buffer, 0, bytesRead);
            }
            fis.close();

            byte[] imageBytes=baos.toByteArray();
            System.out.println("Image converted to byte array. Size- "+imageBytes.length + " bytes");

            //writing byte array back to new image using ByteArrayInputStream
            ByteArrayInputStream bais =new ByteArrayInputStream(imageBytes);
            FileOutputStream fos=new FileOutputStream(destinationFile);

            while ((bytesRead = bais.read(buffer))!=-1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.close();
            bais.close();

            System.out.println("Byte array written back to new image file- "+destinationFile);

            //Verify that the new file is identical to the original image
            if (compareFiles(sourceFile, destinationFile)) {
                System.out.println("Both files are identical.");
            } else {
                System.out.println("Files are different.");
            }

        } catch (IOException e) {
            System.out.println("Error occurred- "+e.getMessage());
        }
    }

    private static boolean compareFiles(String file1, String file2) throws IOException {
        FileInputStream fis1 =new FileInputStream(file1);
        FileInputStream fis2 =new FileInputStream(file2);

        int b1;
        int b2;
        while ((b1 = fis1.read())!= -1 && (b2=fis2.read())!= -1) {
            if (b1 != b2) {
                fis1.close();
                fis2.close();
                return false;
            }
        }

        boolean ans =(fis1.read()== -1 && fis2.read()== -1);
        fis1.close();
        fis2.close();
        return ans;
    }
}
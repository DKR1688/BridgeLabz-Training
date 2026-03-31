import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public class LargeFileReading {
	public static void main(String[] args) throws Exception{
		
		File file = new File("fileName.txt");
        long start;
        long end;

        //fileReader
        start =System.nanoTime();
        FileReader fr = new FileReader(file);
        while (fr.read() != -1) {
        // reading character by character
        }
        fr.close();
        end = System.nanoTime();
        System.out.println("FileReader Time: " + (end - start) / 1_000_000 + " ms");

        //inputStreamReader
        start = System.nanoTime();
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
        while (isr.read() != -1) {
        // reading bytes and converting to characters
        }
        isr.close();
        end = System.nanoTime();
        System.out.println("InputStreamReader Time: " + (end - start) / 1_000_000 + " ms");

	}
}

import java.io.*;
public class PipedStream {
	public static void main(String[] args) {
        try {
            //piped streams
            PipedOutputStream pos =new PipedOutputStream();
            PipedInputStream pis =new PipedInputStream(pos); // Connect streams

            //threads
            WriterThread writer =new WriterThread(pos);
            ReaderThread reader =new ReaderThread(pis);

            writer.start();
            reader.start();
        } catch (IOException e) {
            System.out.println("Main IOException- "+e.getMessage());
        }
    }
}

class WriterThread extends Thread {
    private PipedOutputStream pos;

    WriterThread(PipedOutputStream pos) {
        this.pos=pos;
    }

    @Override
    public void run() {
        try {
            String message ="Hello from Writer thread.";
            System.out.println("Writer- Writing message.. "+message);

            //writing data as bytes
            pos.write(message.getBytes());
            pos.close();
        } catch (IOException e) {
            System.out.println("Writer IOException- "+e.getMessage());
        }
    }
}

class ReaderThread extends Thread {
    private PipedInputStream pis;

    ReaderThread(PipedInputStream pis) {
        this.pis=pis;
    }

    @Override
    public void run() {
        try {
            byte[] buffer=new byte[1024];
            int bytesRead=pis.read(buffer);

            //converting bytes to string
            String received =new String(buffer, 0, bytesRead);
            System.out.println("Reader- Received message... "+received);
            pis.close();
        } catch (IOException e) {
            System.out.println("Reader IOException- "+e.getMessage());
        }
    }
}

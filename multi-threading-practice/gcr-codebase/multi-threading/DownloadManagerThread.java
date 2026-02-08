import java.util.Random;

public class DownloadManagerThread {
	public static void main(String[] args) {
		FileDownloader d1 =new FileDownloader("Document.pdf");
		FileDownloader d2 =new FileDownloader("Image.jpg");
		FileDownloader d3 =new FileDownloader("Video.mp4");

		d1.start();
		d2.start();
		d3.start();

		try {
			d1.join();
			d2.join();
			d3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("All downloads completed.");
	}
}

class FileDownloader extends Thread {
	private String fileName;
	private Random random=new Random();

	FileDownloader(String fileName) {
		this.fileName=fileName;
	}

	@Override
	public void run() {
		for (int progress = 0; progress<=100; progress += 10) {
			System.out.println("[" + Thread.currentThread().getName() + "] Downloading " + fileName + ": " + progress + "%");
			try {
				Thread.sleep(random.nextInt(400) + 100); //it is random delay
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

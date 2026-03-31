
public class BackgroundJobExecution {
    public static void main(String[] args) {

        //runnable task for background job
        Runnable bgTask = () -> {
            System.out.println("Background task started...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Background task completed!");
        };

        Thread workerThread = new Thread(bgTask);
        workerThread.start();

        System.out.println("Main thread continues executing...");
    }
}


public class ShopJobScheduler {
    public static void main(String[] args) {
        System.out.println("Starting print jobs...");

        long overallStart = System.currentTimeMillis();

        //creating threads with jobs
        Thread job1 =new Thread(new PrintJob("Job1", 10), "Job1");
        Thread job2 =new Thread(new PrintJob("Job2", 5), "Job2");
        Thread job3 =new Thread(new PrintJob("Job3", 15), "Job3");
        Thread job4 =new Thread(new PrintJob("Job4", 8), "Job4");
        Thread job5 =new Thread(new PrintJob("Job5", 12), "Job5");

        //setting priorities
        job1.setPriority(5);
        job2.setPriority(8);
        job3.setPriority(3);
        job4.setPriority(6);
        job5.setPriority(7);

        job1.start();
        job2.start();
        job3.start();
        job4.start();
        job5.start();

        try {
            job1.join();
            job2.join();
            job3.join();
            job4.join();
            job5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long overallEnd = System.currentTimeMillis();
        System.out.println("All jobs completed in " + (overallEnd - overallStart) + "ms");
    }
}

class PrintJob implements Runnable {
    private String jobName;
    private int pages;

    public PrintJob(String jobName, int pages) {
        this.jobName = jobName;
        this.pages = pages;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        for (int i=1; i<=pages; i++) {
            System.out.println("Printing " + jobName + " - Page " + i + " of " + pages);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(jobName + " completed in " + (end - start) + "ms.");
    }
}

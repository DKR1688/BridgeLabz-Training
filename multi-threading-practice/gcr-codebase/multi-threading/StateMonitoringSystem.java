import java.text.SimpleDateFormat;
import java.util.Date;
public class StateMonitoringSystem {
	public static void main(String[] args) {
		TaskRunner task1=new TaskRunner("Task-1");
		TaskRunner task2=new TaskRunner("Task-2");

		//monitoring before starting
		System.out.println("[Monitor] " + task1.getName() + " is in " + task1.getState());
		System.out.println("[Monitor] " + task2.getName() + " is in " + task2.getState());

		task1.start();
		task2.start();

		StateMonitor monitor = new StateMonitor(new Thread[] { task1, task2 });
		monitor.start();

		try {
			task1.join();
			task2.join();
			monitor.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("All tasks and monitoring complete!");
	}
}

class TaskRunner extends Thread {
	public TaskRunner(String name) {
		super(name);
	}

	@Override
	public void run() {
		try {
			//sleep for 2 seconds
			Thread.sleep(2000);

			//perform computation
			int sum=0;
			for (int i=1; i<=100000; i++) {
				sum += i;
			}
			System.out.println(getName() + " finished computation. Sum=" + sum);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class StateMonitor extends Thread {
	private Thread[] threads;

	public StateMonitor(Thread[] threads) {
		this.threads = threads;
	}

	@Override
	public void run() {
		boolean monitoring = true;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		while (monitoring) {
			monitoring = false;
			for (Thread t :threads) {
				Thread.State state =t.getState();
				String timeStamp = sdf.format(new Date());
				System.out.println("[Monitor] " + t.getName() + " is in " + state + " state at " + timeStamp);

				if (state != Thread.State.TERMINATED) {
					monitoring = true;
				}
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (Thread t : threads) {
			System.out.println("Summary: " + t.getName() + " went through 4 states");
		}
	}
}

package LinkedList;
//Circular Linked List: Round Robin Scheduling Algorithm

class ProcessNode {
	int pid; // Process ID
	int burstTime; // Total CPU time required
	int remainingTime; // Time left for execution
	int priority; // Priority (not used in RR but kept for extension)
	int completionTime; // Time when process finishes execution
	ProcessNode next; // Pointer to next process (circular list)

	// Constructor to initialize a process
	ProcessNode(int pid, int burstTime, int priority) {
		this.pid = pid;
		this.burstTime = burstTime;
		this.remainingTime = burstTime;
		this.priority = priority;
		this.next = null;
	}
}

//Class that implements Round Robin Scheduling using Circular Linked List
class RoundRobinScheduler {

	ProcessNode head = null; // Head of circular linked list
	int timeQuantum; // Fixed time slice for each process
	int currentTime = 0; // Tracks total elapsed CPU time
	int processCount = 0; // Number of active processes

	// Constructor
	RoundRobinScheduler(int timeQuantum) {
		this.timeQuantum = timeQuantum;
	}

	// 1. Add a new process at the end of the circular queue
	public void addProcess(int pid, int burstTime, int priority) {
		ProcessNode newNode = new ProcessNode(pid, burstTime, priority);
		processCount++;

		// If queue is empty
		if (head == null) {
			head = newNode;
			newNode.next = head;
			return;
		}

		// Traverse to last node
		ProcessNode temp = head;
		while (temp.next != head) {
			temp = temp.next;
		}

		// Insert at the end
		temp.next = newNode;
		newNode.next = head;
	}

	// 2. Remove a completed process from the queue
	private void removeProcess(ProcessNode prev, ProcessNode curr) {

		// If only one process exists
		if (curr == head && curr.next == head) {
			head = null;
		} else {
			// If head is being removed
			if (curr == head) {
				head = head.next;
			}
			prev.next = curr.next;
		}
		processCount--;
	}

	// 3. Execute Round Robin Scheduling
	public void execute() {

		if (head == null) {
			System.out.println(" No processes available for execution.");
			return;
		}

		ProcessNode curr = head;
		ProcessNode prev = null;

		System.out.println("\nROUND ROBIN SCHEDULING STARTED- ");
		System.out.println("Time Quantum: " + timeQuantum);

		// Continue until all processes finish
		while (processCount > 0) {

			System.out.println("\n⏰ Current CPU Time: " + currentTime);
			System.out.println("Processes in Ready Queue:");
			displayProcesses();

			// If process needs more than one time quantum
			if (curr.remainingTime > timeQuantum) {

				System.out.println(" Process P" + curr.pid + " executes for " + timeQuantum + " units.");

				curr.remainingTime -= timeQuantum;
				currentTime += timeQuantum;

				prev = curr;
				curr = curr.next;

			} else {
				// Process finishes execution
				System.out.println(
						" Process P" + curr.pid + " executes for " + curr.remainingTime + " units and completes.");

				currentTime += curr.remainingTime;
				curr.remainingTime = 0;
				curr.completionTime = currentTime;

				System.out.println(" Process P" + curr.pid + " completed at time " + currentTime);

				removeProcess(prev, curr);
				curr = (prev == null) ? head : prev.next;
			}
		}

		System.out.println("\nALL PROCESSES COMPLETED- ");
	}

	// 4. Display remaining execution time of all processes
	private void displayProcesses() {

		if (head == null) {
			System.out.println("Queue is empty.");
			return;
		}

		ProcessNode temp = head;
		do {
			System.out.println("Process P" + temp.pid + " | Remaining Time: " + temp.remainingTime);
			temp = temp.next;
		} while (temp != head);
	}

	// 5. Calculate and display Waiting Time and Turnaround Time
	public void calculateTimes(ProcessNode[] processes) {

		double totalWT = 0;
		double totalTAT = 0;

		System.out.println("\n PROCESS STATISTICS- ");
		System.out.println("PID\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");

		for (ProcessNode p : processes) {

			int turnaroundTime = p.completionTime;
			int waitingTime = turnaroundTime - p.burstTime;

			totalWT += waitingTime;
			totalTAT += turnaroundTime;

			System.out.println("P" + p.pid + "\t" + p.burstTime + "\t\t" + p.completionTime + "\t\t" + turnaroundTime
					+ "\t\t" + waitingTime);
		}

		System.out.println("\n Average Waiting Time: " + (totalWT / processes.length));
		System.out.println(" Average Turnaround Time: " + (totalTAT / processes.length));
	}
}

//Main class
public class RoundRobinScheduling {

	public static void main(String[] args) {

		int timeQuantum = 4;
		RoundRobinScheduler scheduler = new RoundRobinScheduler(timeQuantum);

		// Store original processes for calculating times later
		ProcessNode[] processes = new ProcessNode[] { new ProcessNode(1, 10, 1), new ProcessNode(2, 5, 2),
				new ProcessNode(3, 8, 1) };

		// Add processes to scheduler
		for (ProcessNode p : processes) {
			scheduler.addProcess(p.pid, p.burstTime, p.priority);
		}

		// Run scheduling
		scheduler.execute();

		// Display waiting time and turnaround time
		scheduler.calculateTimes(processes);
	}
}

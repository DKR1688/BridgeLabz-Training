package LinkedList;
// Circular Linked List: Task Scheduler

public class TaskScheduler {
	public static void main(String[] args) {
		CircularLinkedList schedule= new CircularLinkedList();

		schedule.addTask(1, "Code Review", 2, "08-Jan-2026");
		schedule.addTask(2, "Write Tests", 3, "09-Jan-2026");
		schedule.addAtBeginning(3, "Fix Bug", 1, "10-Jan-2026");
		schedule.addAtPosition(4, "Deploy", 1, "09-Jan-2026", 2);
		System.out.println();

		System.out.println("All tasks:");
		schedule.displayAll();
		System.out.println();

		schedule.viewCurrent();
		schedule.nextTask();
		schedule.nextTask();
		System.out.println();

		schedule.searchByPriority(1);
		System.out.println();

		schedule.removeTask(2);
		System.out.println("After removing Task 2 is- ");
		schedule.displayAll();
		System.out.println();
	}
}

//node class for each Task
class Node {
	int taskId;
	String taskName;
	int priority;
	String dueDate;
	Node next;

	Node(int id, String name, int p, String date) {
		taskId =id;
		taskName =name;
		priority =p;
		dueDate =date;
		next =null;
	}
}

//circular linked List for task schedule
class CircularLinkedList {
	Node head =null;
	Node tail =null;
	Node current =null;

	//adding task at end
	public void addTask(int id, String name, int p, String date) {
		Node newNode =new Node(id, name, p, date);
		if (head==null) {
			head = tail = current = newNode;
			newNode.next =head;
		} else {
			tail.next =newNode;
			newNode.next =head;
			tail =newNode;
		}
	}

	//adding task at beginning
	public void addAtBeginning(int id, String name, int p, String date) {
		Node newNode = new Node(id, name, p, date);
		if (head==null) {
			head =tail = current =newNode;
			newNode.next =head;
		} else {
			newNode.next =head;
			head =newNode;
			tail.next =head;
		}
	}

	//adding task at position
	public void addAtPosition(int id, String name, int p, String date, int position) {
		if (position<=1) {
			addAtBeginning(id, name, p, date);
			return;
		}
		Node newNode =new Node(id, name, p, date);
		Node temp =head;
		for (int i=1; i<position- 1; i++) {
			temp =temp.next;
		}
		newNode.next =temp.next;
		temp.next =newNode;
		if (temp ==tail)
			tail =newNode;
	}

	//remove task by ID
	public void removeTask(int id) {
		if (head ==null)
			return;

		Node temp = head, prev = tail;
		do {
			if (temp.taskId ==id) {
				if (temp ==head)
					head =head.next;
				if (temp==tail)
					tail = prev;
				prev.next=temp.next;
				if (current == temp)
					current = temp.next;
				return;
			}
			prev = temp;
			temp = temp.next;
		} while (temp != head);
	}

	//viewing current task
	public void viewCurrent() {
		if (current != null)
			System.out.println("Current task is- " +current.taskId + " | " + current.taskName);
	}

	//moving to next task
	public void nextTask() {
		if (current != null) {
			current =current.next;
			System.out.println("Moved to- " + current.taskId + " | " + current.taskName);
		}
	}

	//displaying all tasks
	public void displayAll() {
		if (head==null) {
			System.out.println("No tasks.");
			return;
		}
		Node temp =head;
		do {
			System.out.println(
					temp.taskId+" | " +temp.taskName + " | Priority is- "+ temp.priority + " | Due to- "+ temp.dueDate);
			temp = temp.next;
		} while (temp!=head);
	}

	//searching by priority
	public void searchByPriority(int p) {
		if (head==null) {
			return;
		}
		Node temp=head;
		System.out.println("Tasks with priority "+p+ " is- ");
		do {
			if (temp.priority==p)
				System.out.println(temp.taskId + " | " + temp.taskName);
			temp = temp.next;
		} while (temp != head);
	}
}
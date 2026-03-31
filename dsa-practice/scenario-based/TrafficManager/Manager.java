package TrafficManager;

public class Manager {
	public static void main(String[] args) {
		QueueManage waiting = new QueueManage(3);
        Traffic manager = new Traffic();

        try {
            waiting.enqueue("CAR-101");
            waiting.enqueue("CAR-102");
            waiting.enqueue("CAR-103");

            manager.addToRoundabout(waiting.dequeue());
            manager.addToRoundabout(waiting.dequeue());

            manager.printRoundabout();

            manager.removeFromRoundabout("CAR-101");
            manager.printRoundabout();

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

	}
}

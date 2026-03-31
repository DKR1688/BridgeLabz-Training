package TrafficManager;

import java.util.LinkedList;
import java.util.Queue;

public class QueueManage {
	int capacity;
    Queue<String> queue = new LinkedList<>();

    QueueManage(int capacity) {
        this.capacity = capacity;
    }

    void enqueue(String vehicle) {
        if (queue.size() == capacity) {
            throw new RuntimeException("Queue Overflow: Waiting area is full!");
        }
        queue.add(vehicle);
        System.out.println(vehicle + " added to waiting queue");
    }

    String dequeue() {
        if (queue.isEmpty()) {
            throw new RuntimeException("Queue Underflow: No vehicles waiting!");
        }
        return queue.poll();
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }
}

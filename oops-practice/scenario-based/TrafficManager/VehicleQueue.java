package TrafficManager;

public class VehicleQueue {
	String[] queue;
    int front;
    int rear;
    int size;
    int capacity;

    VehicleQueue(int capacity) {
        this.capacity =capacity;
        queue = new String[capacity];
        front =0;
        rear =-1;
        size =0;
    }

    public void enqueue(String number) {
        if (size == capacity) {
            System.out.println("Queue Overflow, Car " +number +" cannot wait.");
            return;
        }
        rear = (rear+1)%capacity;
        queue[rear] = number;
        size++;
        System.out.println("Car number is- "+number+" is waiting in queue.");
    }

    public String dequeue() {
        if (size==0) {
            System.out.println("Queue underflow, No cars waiting.");
            return null;
        }
        String car = queue[front];
        front = (front+1)%capacity;
        size--;
        return car;
    }

    public boolean isEmpty() {
        return size==0;
    }
}

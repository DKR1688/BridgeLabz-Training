package QueueInterface;
import java.util.*;
public class ReverseAQueue {
	public static void main(String[] args) {
		Queue<Integer> queue=new LinkedList<>();
		queue.add(10);
		queue.add(20);
		queue.add(30);
		System.out.println("Queue is- "+queue);
		
		Stack<Integer> stack=new Stack<>();
        while (!queue.isEmpty()) {
            stack.push(queue.remove());
        }
        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }
        System.out.println("Reversed queue is- "+queue);
	}
}

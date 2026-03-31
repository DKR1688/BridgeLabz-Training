package QueueInterface;
import java.util.*;
public class StackUsingQueues {
	public static void main(String[] args) {
		Queue<Integer> queue1=new LinkedList<>();
		Queue<Integer> queue2=new LinkedList<>();
		//push
		queue1.add(1);
		queue1.add(2);
		queue1.add(3);
		
		//pop
		while(queue1.size()>1) {
			queue2.add(queue1.poll());
		}
		int popped= queue1.poll();
		System.out.println("Pop last element- "+popped);
		
		//Top
		while(queue2.size()>1) {
			queue1.add(queue2.poll());
		}
		int top=queue2.peek();
		System.out.println("Top element is- "+top);
	}
}

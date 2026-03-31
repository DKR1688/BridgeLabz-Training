package QueueInterface;
import java.util.*;
public class BinaryNumberUsingQueue {
	public static void main(String[] args) {
		Queue<String> queue=new LinkedList<>();

		int N=10;
		queue.add("1");

		List<String> ans=new ArrayList<>();
		for(int i=0; i<N; i++) {
			String curr=queue.poll();
			ans.add(curr);
			
			queue.add(curr+ "0");
			queue.add(curr+ "1");
		}
		System.out.println(ans);
	}
}

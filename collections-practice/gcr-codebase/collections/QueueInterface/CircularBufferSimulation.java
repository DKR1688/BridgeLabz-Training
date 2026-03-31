package QueueInterface;
import java.util.*;
public class CircularBufferSimulation {
	public static void main(String[] args) {
		int capacity=3;
		int[] buffer=new int[capacity];
		int size=0;
		int front=0;
		int rear=0;
		
		int[] inserts= {1, 2, 3, 4};
		for(int insert: inserts) {
			buffer[rear]=insert;
			rear =(rear+1)%capacity;
			
			if(size<capacity) {
				size++;
			}else {
				front= (front+1)%capacity;
			}
			
			System.out.print("Buffer is- [");
			for(int i=0; i<size; i++) {
				System.out.print(buffer[(front+i)%capacity]);
				
				if(i<size-1) {
					System.out.print(", ");
				}
			}
			System.out.println("]");
		}
	}
}

package ListInterface;
import java.util.*;
public class NthElementFromEnd {
    public static void main(String[] args) {
        LinkedList<Character> list = new LinkedList<>();
        list.add('A');
        list.add('B');
        list.add('C');
        list.add('D');
        list.add('E');

        int N=2;
        //we will use two-pointer approach
        Iterator<Character> fast =list.iterator();
        Iterator<Character> slow =list.iterator();

        //we will move fast pointer N steps ahead
        int steps=0;
        Character fastVal=null;
        while (fast.hasNext() && steps<N) {
            fast.next();
            steps++;
        }

        //we will move both fast and slow until fast reaches the end
        Character slowVal = null;
        while (fast.hasNext()) {
            fast.next();
            slow.next();
        }

        if(slow.hasNext()) {
        	//here at this point, slowVal is the Nth element from the end
            System.out.println("Index "+N+" element from end is- " +slow.next());
        }
    }
}
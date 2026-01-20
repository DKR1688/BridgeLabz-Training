class ListNodes {
    int val;
    ListNodes next;
    ListNodes(int x) {
        val = x;
        next = null;
    }
}

public class LinkedListCycle{
    public static boolean hasCycle(ListNodes head) {
        ListNodes slow = head;
        ListNodes fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;  // move slow by 1
            fast = fast.next.next; // move fast by 2
            if (slow == fast) // cycle detected
                return true;
        }
        return false; // no cycle
    }

    public static void main(String[] args) {
        //create nodes
        ListNodes node1 =new ListNodes(3);
        ListNodes node2 =new ListNodes(2);
        ListNodes node3 =new ListNodes(0);
        ListNodes node4 =new ListNodes(-4);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        //to create a cycle (-4 -> 2)
        node4.next = node2;

        boolean ans= hasCycle(node1);
        System.out.println("Does the linked list have a cycle- " +ans);
    }
}

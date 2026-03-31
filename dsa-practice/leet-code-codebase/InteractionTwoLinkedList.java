class ListNode2 {
    int val;
    ListNode2 next;
    ListNode2(int x) {
        val = x;
        next = null;
    }
}

public class InteractionTwoLinkedList {
    public static ListNode2 getIntersectionNode(ListNode2 headA, ListNode2 headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode2 t1 = headA;
        ListNode2 t2 = headB;

        while (t1 != t2) {
            t1 = (t1 == null) ? headB : t1.next;
            t2 = (t2 == null) ? headA : t2.next;
        }

        return t1; //either intersection node or null
    }

    //Helper to convert array to linked list
    public static ListNode2 arrayToLL(int[] arr) {
        if (arr.length==0) {
        	return null;
        }
        ListNode2 head = new ListNode2(arr[0]);
        ListNode2 tail = head;
        for (int i=1; i<arr.length; i++) {
            tail.next = new ListNode2(arr[i]);
            tail = tail.next;
        }
        return head;
    }

    // Helper: get node at index
    public static ListNode2 getNodeAtIndex(ListNode2 head, int index) {
        int i = 0;
        while (head!=null && i<index) {
            head =head.next;
            i++;
        }
        return head;
    }

    // Helper: attach list B to target node (to create intersection)
    public static void makeYIntersection(ListNode2 headB, ListNode2 target) {
        ListNode2 temp = headB;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next =target;
    }

    public static void main(String[] args) {
        int[] arr1 ={1, 2, 3, 4, 5, 6, 7};
        int[] arr2 ={8, 9};

        ListNode2 headA = arrayToLL(arr1);
        ListNode2 headB = arrayToLL(arr2);

        //Intersection at node with value 4
        ListNode2 intersectionNode = getNodeAtIndex(headA, 3);
        makeYIntersection(headB, intersectionNode);

        ListNode2 ans= getIntersectionNode(headA, headB);

        if (ans!= null) {
            System.out.println("Intersection at node value- " +ans.val);
        } else {
            System.out.println("No intersection found.");
        }
    }
}

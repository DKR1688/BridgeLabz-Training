//singly linked list.
class ListNode {
    int val;
    ListNode next;
    ListNode(){}
    ListNode(int val) { 
    	this.val = val; 
    }
    ListNode(int val, ListNode next) { 
    	this.val = val; 
    	this.next = next; 
    }
}

public class MergeTwoSortedLists {
	public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
		
		//node to simplify edge cases
        ListNode mergedLL =new ListNode(-1);
        ListNode temp =mergedLL;
        
        while(list1 !=null && list2 !=null ){
            if(list1.val <= list2.val){
                temp.next = list1;
                list1 = list1.next;
                temp = temp.next;
            }
            else{
                temp.next = list2;
                list2 = list2.next;
                temp = temp.next;
            }
        }
        while(list1 != null){
            temp.next = list1;
            list1 = list1.next;
                temp = temp.next;
        }
        while(list2 != null){
            temp.next = list2;
            list2 = list2.next;
            temp = temp.next;
        }
        return mergedLL.next;
    }
	
	//helper method to print linked list
	public static void printList(ListNode head) {
	    while (head != null) {
	        System.out.print(head.val + " ");
	        head = head.next;
	    }
	    System.out.println();
	}
	
    public static void main(String[] args) {
        //list1 = [1,2,4]
    	//list2 = [1,3,4]
        ListNode list1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));

        MergeTwoSortedLists solution = new MergeTwoSortedLists();
        ListNode merged = solution.mergeTwoLists(list1, list2);

        printList(merged);
    }
}
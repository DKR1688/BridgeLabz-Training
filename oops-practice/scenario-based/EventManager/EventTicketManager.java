package EventManager;
import java.util.*;
public class EventTicketManager {
	
	//use of quick sort threw recursive partitioning
	public static int partition(int[] arr, int low, int high) {
	    int pivot =arr[high]; // pivot element
	    int index =low - 1;

	    for (int i = low; i < high; i++) {
	        if (arr[i] <= pivot) {
	            index++;
	            // swap arr[i] and arr[index]
	            int temp = arr[index];
	            arr[index] = arr[i];
	            arr[i] = temp;
	        }
	    }

	    // place pivot in correct position
	    int temp = arr[index + 1];
	    arr[index + 1] = arr[high];
	    arr[high] = temp;

	    return index + 1; // return pivot index
	}

	public static void sort(int[] arr, int low, int high) {
		if(high>low) {
			int part =partition(arr, low, high);
			sort(arr, low, part-1);
			sort(arr, part+1, high);
		}
	}
	
	public static void main(String[] args) {
		int[] ticketPrices = {310, 720, 980, 1450, 2600, 330, 410, 510, 610, 710,
        	    810, 910, 1010, 1110, 1210, 1310, 1410, 1510, 1610, 1710};

        //Quick sort to arrange the tickets
        sort(ticketPrices, 0, ticketPrices.length-1);
        System.out.println();

        System.out.println("Sorted ticket prices are- " + Arrays.toString(ticketPrices));

        //printing top 50 cheapest and expensive
        System.out.println("Top 50 cheapest tickets are- ");
        for (int i=0; i<Math.min(50, ticketPrices.length); i++) {
            System.out.print(ticketPrices[i] +" ");
        }
        System.out.println();
        
        System.out.println("Top 50 expensive tickets are- ");
        for (int i=ticketPrices.length-1; i>= Math.max(ticketPrices.length-50, 0); i--) {
            System.out.print(ticketPrices[i] + " ");
        }
    }
}

import java.util.*;
public class SortingLargeData {
	public static void main(String[] args) {
		int n=10000;
        int numbers[] =new int[n];
        Random rand =new Random();

        for (int i=0; i<n; i++) {
            numbers[i] =rand.nextInt(n);
        }

        int[] arr1 =numbers.clone();
        int[] arr2 =numbers.clone();
        int[] arr3 =numbers.clone();

        long start;
        long end;
        
        //bubble sort
        start =System.nanoTime();
        bubbleSort(arr1);
        end = System.nanoTime();
        System.out.println("Bubble sort time is- " +(end-start)/1000000 + " ms");

        //merge Sort
        start = System.nanoTime();
        mergeSort(arr2, 0, arr2.length-1);
        end =System.nanoTime();
        System.out.println("Merge sort time is- " +(end-start) /1000000 + " ms");

        //quick Sort
        start =System.nanoTime();
        quickSort(arr3, 0, arr3.length - 1);
        end = System.nanoTime();
        System.out.println("Quick Sort Time: " + (end - start) / 1_000_000 + " ms");

	}
	
    public static void bubbleSort(int[] numbers) {
        int n =numbers.length;
        for (int i=0; i<n-1; i++) {
            for (int j=0; j< n-i-1; j++) {
                if (numbers[j] >numbers[j+1]) {
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
    }
    
    public static void mergeSort(int numbers[], int left, int right) {
        if (left<right) {
            int mid = (left+right)/2;
            mergeSort(numbers, left, mid);
            mergeSort(numbers, mid + 1, right);
            merge(numbers, left, mid, right);
        }
    }
    private static void merge(int numbers[], int left, int mid, int right) {
        int n1 = mid-left+1;
        int n2 = right-mid;

        int[] L =new int[n1];
        int[] R =new int[n2];

        for (int i=0; i<n1; i++)
            L[i] = numbers[left+i];
        for (int j=0; j<n2; j++)
            R[j] = numbers[mid+1+j];

        int i = 0;
        int j = 0;
        int k =left;

        while (i<n1 && j<n2) {
            if (L[i] <= R[j])
            	numbers[k++] =L[i++];
            else
            	numbers[k++] =R[j++];
        }

        while (i<n1)
        	numbers[k++] =L[i++];

        while (j<n2)
        	numbers[k++] =R[j++];
    }
    
    // Quick Sort
    public static void quickSort(int numbers[], int low, int high) {
        if (low <high) {
            int pivot = partition(numbers, low, high);
            quickSort(numbers, low, pivot - 1);
            quickSort(numbers, pivot + 1, high);
        }
    }

    private static int partition(int numbers[], int low, int high) {
        int pivot = numbers[high];
        int i = low-1;

        for (int j = low; j<high; j++) {
            if (numbers[j] < pivot) {
                i++;
                int temp = numbers[i];
                numbers[i] = numbers[j];
                numbers[j] = temp;
            }
        }

        int temp = numbers[i + 1];
        numbers[i + 1] = numbers[high];
        numbers[high] = temp;

        return i+1;
    }
}
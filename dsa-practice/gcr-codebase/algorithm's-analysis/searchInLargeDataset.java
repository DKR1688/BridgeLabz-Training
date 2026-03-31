//the performance of Linear Search (O(N)) and Binary Search (O(log N)) on different dataset sizes.
import java.util.Arrays;
public class searchInLargeDataset {
	public static void main(String[] args) {
		int[] dataSetSizes ={1000, 10000, 1000000};
		int target =-1;
		
		for(int size: dataSetSizes) {
			int[] dataSet =new int[size];
			for(int i=0; i<size; i++) {
				dataSet[i] =i;
			}
			
			//Linear search O(N) timing
            long startLinear =System.nanoTime();
            linearSearch(dataSet, target);
            long endLinear = System.nanoTime();

            //Binary search O(N) timing
            Arrays.sort(dataSet); //binary search requires sorted array
            long startBinary =System.nanoTime();
            binarySearch(dataSet, target);
            long endBinary =System.nanoTime();

            double linearTime =(endLinear-startLinear) /1e6;
            double binaryTime =(endBinary-startBinary) /1e6;

            //Binary Search performs much better for large datasets, provided data is sorted.
            System.out.println("DataSet size- "+size+"\n Linear search in ms- "+linearTime+"\n Binary search in ms- "+binaryTime);
        }
	}
	
	public static int linearSearch(int[] arr, int target) {
		for(int i=0; i<arr.length; i++) {
			if(arr[i]==target) {
				return i;
			}
		}
		return -1;
	}
	
	public static int binarySearch(int[] arr, int target) {
		int ans =Arrays.binarySearch(arr, target);
		return ans;
	}
}

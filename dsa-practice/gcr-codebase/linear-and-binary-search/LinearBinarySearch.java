
import java.util.Arrays;
public class LinearBinarySearch {
    public static void main(String[] args) {
        int[] numbers ={5, 3, 8, -5, 4, -1, -3, 1, 9};
        int target =4;

        //find the first missing positive integer in the list using linear search
        int missing =findFirstMissingPositive(numbers);
        System.out.println("First missing positive integer- " +missing);

        //find the index of a given target number using binary search
        Arrays.sort(numbers);
        System.out.println("Sorted array for binary search is- " +Arrays.toString(numbers));

        int targetIndex =binarySearch(numbers, target);
        if (targetIndex != -1) {
            System.out.println("Target " + target+" found at sorted index- " +targetIndex);
        } else {
            System.out.println("Target " +target+" not found.");
        }
    }

    //Linear Search for the first missing positive integer
    public static int findFirstMissingPositive(int[] nums) {
        int len =nums.length;

        for (int i=0; i<len; i++) {
            while (nums[i]>0 && nums[i]<=len && nums[nums[i]-1] != nums[i]) {
                //Swap nums[i] with the element at its target position
                int temp = nums[nums[i]-1];
                nums[nums[i]-1] = nums[i];
                nums[i] = temp;
            }
        }

        //linear search for the first index that doesn't match its value
        for (int i=0; i<len; i++) {
            if (nums[i] != i+1) {
                return i+1;
            }
        }
        return len+1;
    }

    //Binary Search for the target index
    public static int binarySearch(int[] arr, int target) {
        int low=0;
        int high = arr.length-1;

        while (low<=high) {
            int mid = low + (high-low)/2;

            if (arr[mid]==target) {
            	return mid;
            }
            if (arr[mid]<target) {
            	low = mid+1;
            }
            else {
            	high = mid-1;
            }
        }
        return -1;
    }
}

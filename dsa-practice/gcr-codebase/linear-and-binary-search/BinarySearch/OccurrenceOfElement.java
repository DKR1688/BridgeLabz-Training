package BinarySearch;

public class OccurrenceOfElement {
    public static void main(String[] args) {
        int[] arr ={1, 2, 4, 5, 5, 5, 7, 9};
        int target =5;

        int first =findFirstOccurrence(arr, target);
        int last =findLastOccurrence(arr, target);

        if (first==-1 || last==-1) {
            System.out.println("Element "+target+" not found.");
        } else {
            System.out.println("First occurrence index is- " +first);
            System.out.println("Last occurrence index is- " +last);
        }
    }

    //finding first occurrence using binary search
    public static int findFirstOccurrence(int[] arr, int target) {
        int low=0;
        int high = arr.length-1;
        int ans= -1;

        while (low <=high) {
            int mid = low+(high-low)/2;

            if (arr[mid] ==target) {
                ans= mid; 
                high = mid-1;    //we will keep searching on the left side
            } else if (arr[mid]<target) {
                low = mid+1;
            } else {
                high = mid-1;
            }
        }
        return ans;
    }

    //finding last occurrence using binary search
    public static int findLastOccurrence(int[] arr, int target) {
        int low =0;
        int high =arr.length-1;
        int ans =-1;

        while (low <=high) {
            int mid = low+(high-low)/2;

            if (arr[mid]==target) {
                ans= mid;
                low = mid+1;     //we will keep searching on the right side
            } else if (arr[mid] <target) {
                low = mid+1;
            } else {
                high = mid-1;
            }
        }
        return ans;
    }
}
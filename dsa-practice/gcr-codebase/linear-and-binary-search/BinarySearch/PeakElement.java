package BinarySearch;

public class PeakElement {
    public static void main(String[] args) {
        int[] arr ={1, 2, 45, 8, 3, 20, 4, 1, 0};
        
        int peakIndex =findPeakElement(arr);
        System.out.println("Peak element is "+arr[peakIndex]+" at index " +peakIndex);
    }

    //finding a peak element using binary search
    public static int findPeakElement(int[] arr) {
        int n =arr.length;
        int left =0;
        int right = n-1;

        while (left <=right) {
            int mid = left+(right-left)/2;

            //If arr[mid] > arr[mid - 1] and arr[mid] > arr[mid + 1], arr[mid] is a peak element
            boolean leftCheck = (mid==0 || arr[mid] > arr[mid-1]);
            boolean rightCheck = (mid == n-1 || arr[mid] > arr[mid+1]);

            if (leftCheck && rightCheck) {
                return mid;
            }

            //if left neighbor is greater, move left
            if (mid>0 && arr[mid-1] > arr[mid]) {
                right = mid-1;
            } else {
                left =mid+1;
            }
        }
        return -1; //we should never reach here if array has at least one peak
    }
}

package BinarySearch;

public class RatationPoint {
	public static void main(String[] args) {
        int[] arr ={57, 89, 25, 31, 64, 12, 4, 8, 56};
        int ans =findRotationPoint(arr);
        
        System.out.println("Indext of rotation point is- " +ans);
        System.out.println("Smallest number is- " + arr[ans]);
    }
	
	public static int findRotationPoint(int[] arr) {
        int left =0;
        int right =arr.length-1;

        while (left<right) {
            int mid = (left+right)/2;

            //when mid element is greater than the rightmost element and smallest element lies in the right half
            if (arr[mid] >arr[right]) {
                left =mid+1;
            } else {
                right =mid;
            }
        }

        //at the array end when left==right then pointing to the smallest element
        //this is the index of rotation point
        return left;
    }
}

import java.util.Arrays;

public class RemoveDuplicate {
    public static void main(String[] args) {
        //array with duplicates
        int[] nums= {1, 1, 2, 7, 6, 2, 9, 1, 5, 6};
        Arrays.sort(nums);
        int k= removeDuplicates(nums); // Calling method to remove duplicates
        System.out.println("Number of duplicate elements are- " + k);
        System.out.print("Unique elements are- ");
        for (int i = 0; i < k; i++) {
            System.out.print(nums[i] + " ");
        }
    }

    //method to remove duplicates from a sorted array and returns the count of unique elements
    public static int removeDuplicates(int[] nums) {
    	
        if (nums.length==0) {
        	return 0;
        }
        int i=0; //pointer to track position of last unique element

        //start from second element and compare with previous
        for (int j=1; j <nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        //return count of unique elements here i is index but we will add 1 to unique
        return i+1;
    }
}
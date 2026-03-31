public class RemoveDuplicate{
	public static void main(String[] args) {
        int[] nums1 = {1, 1, 2};
        int k1 = removeDuplicates(nums1);
        
        System.out.println("k = " + k1);
        for (int i=0; i<k1; i++) {
        	System.out.print(nums1[i] + " ");
        }
        System.out.println();

        int[] nums2 = {0,0,1,1,1,2,2,3,3,4};
        int k2 = removeDuplicates(nums2);
        
        System.out.println("k = " + k2);
        for (int i = 0; i < k2; i++) {
        	System.out.print(nums2[i] + " ");
        }
    }
	
	//this method is to remove duplicate and returning number of duplicate element
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        int i=0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j]; //placing unique element
            }
        }
        return i + 1;// number of unique elements
    }
}
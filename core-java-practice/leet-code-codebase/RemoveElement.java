public class RemoveElement {

    // this method to remove all occurrences of val from nums
    public static int removeElement(int[] nums, int val) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[j] = nums[i];
                j++;
            }
        }
        return j; // it will be new length after removal
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 2, 3, 5, 2, 7};
        int val = 3;

        int newLength = removeElement(nums, val);

        System.out.println("New length: " + newLength);
        System.out.print("Array after removal: ");

        // it will print elements up to new length
        for (int i = 0; i < newLength; i++) {
            System.out.print(nums[i] + " ");
        }
    }
}
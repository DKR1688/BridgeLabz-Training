public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {

        // Loop through each element 
        for (int i = 0; i < nums.length; i++) {

            // Compare with every other element after it
            for (int j = i + 1; j < nums.length; j++) {

                // Check if the two numbers add up to the target
                if (nums[i] + nums[j] == target) {

                    // If yes, then return their indices
                    return new int[] { i, j };
                }
            }
        }
        // If no pair found, then return empty array
        return new int[] {};
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 3};
        int target = 4;

        int[] result = twoSum(nums, target);

        if (result.length > 0) {
            System.out.println("Indices: " + result[0] + ", " + result[1]);
        } else {
            System.out.println("No solution found.");
        }
    }
}
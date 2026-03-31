import java.util.*;
public class MinimumBitwiseArray {
	public static int[] minBitwiseArray(List<Integer> nums) {
        int[] ans = new int[nums.size()];
        Arrays.fill(ans, -1);

        for (int i=0; i<nums.size(); i++) {
            for (int j=0; j<nums.get(i); j++) {
                if ((j | (j+1)) == nums.get(i)) {
                    ans[i] = j;
                    break;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(2, 3, 5, 7, 8);

        int[] ans= minBitwiseArray(nums);

        System.out.print("Output- [");
        for (int i=0; i <ans.length; i++) {
            System.out.print(ans[i]);
            if (i <ans.length - 1) {
            	System.out.print(", ");
            }
        }
        System.out.println("]");
    }


}

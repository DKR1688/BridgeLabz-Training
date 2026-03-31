package BinarySearch;

public class TargetIn2DSortedMatrix {
    public static void main(String[] args) {
        int[][] matrix ={{ 1,  3,  5,  7 },
        				 { 10, 11, 16, 20 },
        				 { 23, 30, 34, 60 }};

        int target=3;
        boolean found =searchMatrix(matrix, target);

        if (found) {
            System.out.println("Found");
        } else {
            System.out.println("Not found");
        }
    }

    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length==0) {
        	return false;
        }
        int rows =matrix.length;
        int cols =matrix[0].length;

        int low=0;
        int high = (rows*cols)-1;

        //Find the middle element index mid = (left + right) / 2.
        while (low <= high) {
            int mid = low+(high-low)/2;

            //Convert mid to row and column indices using row = mid / numColumns and col = mid % numColumns.
            int midRow = mid/cols;
            int midCol = mid%cols;
            int midValue = matrix[midRow][midCol];

            if (midValue == target) {
                System.out.println("Element found at row- "+midRow +", Column- "+midCol);
                return true;
            } else if (midValue<target) {
                low = mid+1;
            } else {
                high = mid-1;
            }
        }
        return false;
    }
}
package sorting;

public class InsertionSort {
    public static void main(String[] args) {
        //employee IDs
        int[] employeeIds = { 105, 101, 110, 103, 108, 102 };

        System.out.println("Ids before sorting");
        for (int employeeId : employeeIds) {
            System.out.println(employeeId);
        }

        InsertionSorting(employeeIds);

        System.out.println("Ids after sorting");
        for (int employeeId : employeeIds) {
            System.out.println(employeeId);
        }
    }

    static void InsertionSorting(int[] array) {
        int n = array.length;

        // Start from the second element (index 1)
        for (int i =1; i < n; i++) {
            int key = array[i]; // The ID we want to insert
            int j = i-1;

            // to one position ahead of their current position
            while (j >= 0 && array[j] > key) {
                array[j+1] = array[j];
                j = j-1;
            }

            //placing the key into its correct spot
            array[j+1] = key;
        }
    }
}

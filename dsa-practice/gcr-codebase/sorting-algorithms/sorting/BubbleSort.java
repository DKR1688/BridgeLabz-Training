package sorting;

public class BubbleSort {
    public static void main(String[] args) {
        //student marks
        int[] marks = { 85, 42, 91, 76, 33, 58 };

        System.out.println("Marks before sorting");
        for (int i : marks) {
            System.out.println(i);
        }

        BubbleSorting(marks);

        System.out.println("Marks after sorting");
        for (int i : marks) {
            System.out.println(i);
        }
    }

    static void BubbleSorting(int[] array) {
        int n = array.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            // Last i elements are already in place, so we ignore them
            for (int j = 0; j < n - i - 1; j++) {
                // Compare adjacent elements
                if (array[j] > array[j + 1]) {
                    // Swap elements
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;

                    swapped = true;
                }
            }

            //If no two elements were swapped in the inner loop, break
            if (!swapped)
                break;
        }
    }
}
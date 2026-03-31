package sorting;

public class MergeSort {
    public static void main(String[] args) {
        //book prices
        double[] bookPrices = {500, 120, 450, 99.99, 750, 200};
        MergeSorting(bookPrices, 0, bookPrices.length-1);

        System.out.println("Sorted Prices (Ascending): ");
        for (double b : bookPrices) {
            System.out.println(b);
        }
    }

    //merge sort to sort the prices in ascending order
    static void MergeSorting(double[] array, int left, int right) {
        if (left<right) {
            int middle = left + (right-left) / 2;

            MergeSorting(array, left, middle);
            MergeSorting(array, middle + 1, right);

            //merging the sorted halves
            Merge(array, left, middle, right);
        }
    }

    static void Merge(double[] array, int left, int middle, int right) {
        //sizes of two subarrays to be merged
        int n1 = middle-left + 1;
        int n2 = right-middle;

        //temporary arrays to merge halves
        double[] leftArray = new double[n1];
        double[] rightArray = new double[n2];

        //copying data into temp arrays
        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, middle + 1, rightArray, 0, n2);

        int i=0;
        int j=0;

        //initial index of merged subarray array
        int k=left;
        while (i<n1 && j<n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        //copying remaining elements of leftArray
        while (i<n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        //copying remaining elements of rightArray
        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }
}
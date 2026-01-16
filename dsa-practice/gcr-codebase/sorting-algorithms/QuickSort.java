package sorting;

public class QuickSort{
    public static void main(String[] args) {
    	double[] productPrices = {500, 120, 450, 99.99, 750, 200};
        System.out.println("Original prices are- ");
        for (double price : productPrices) {
            System.out.println(price);
        }

        quickSort(productPrices, 0, productPrices.length - 1);

        System.out.println("\nSorted prices are- ");
        for (double price : productPrices) {
            System.out.println(price);
        }
    }

    //quickSort method
    static void quickSort(double[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);   // sort left side
            quickSort(arr, pi + 1, high); // sort right side
        }
    }

    //partition method
    static int partition(double[] arr, int low, int high) {
        double pivot =arr[high]; // choose last element as pivot
        int i = low-1;

        for (int j=low; j<high; j++) {
            if (arr[j] < pivot) {
                i++;
                //swap arr[i] and arr[j]
                double temp = arr[i];
                arr[i] =arr[j];
                arr[j] =temp;
            }
        }

        //placing pivot in correct position
        double temp =arr[i + 1];
        arr[i + 1] =arr[high];
        arr[high] =temp;

        return i + 1;
    }
}
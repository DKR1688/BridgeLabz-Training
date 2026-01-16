package sorting;

public class HeapSort{
    // Heap Sort function
    public void sort(int[] arr) {
        int n = arr.length;

        //we start halfway back because the last half are "leaves" (they don't have children).
        //making max heap to sure the highest salary is sitting at the very top (index 0).
        for (int i = n/2-1; i>=0; i--) {
            heapify(arr, n, i);
        }

        //extracting elements one by one
        for (int i= n-1; i>0; i--) {
            //move current root to end
            int temp =arr[0];
            arr[0] =arr[i];
            arr[i] =temp;

            //We need to push the new top value down until the next highest salary is at the root.
            heapify(arr, i, 0);
        }
    }

    //heapify function to ensures the "Parent" salary is always bigger than the "Child" salaries.
    void heapify(int[] arr, int size, int root) {
        int largest =root;
        int left = 2 * root+1;
        int right = 2 * root+2;

        if (left < size && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < size && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != root) {
            int swap =arr[root];
            arr[root] = arr[largest];
            arr[largest] = swap;

            //we keep heapify recursively until it finds its proper level.
            heapify(arr, size, largest);
        }
    }

    public static void main(String[] args) {
        int[] salaries = {55000, 120000, 45000, 75000, 90000, 60000};

        HeapSort sort= new HeapSort();
        sort.sort(salaries);

        System.out.println("Salaries sorted from lowest to highest- ");
        for (int salary : salaries) {
            System.out.print(salary + " ");
        }
    }
}
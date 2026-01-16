package sorting;

public class SelectionSort{
    public static void main(String[] args) {
        int[] examScores = {72, 45, 98, 82, 61, 89};

        selectionSort(examScores);
        System.out.println("\nSorted scores are- ");
        for (int score : examScores) {
            System.out.println(score);
        }
    }

    static void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i=0; i<n-1; i++) {
            int minIndex = i;

            //finding the index of the smallest element
            for (int j =i+1; j <n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            //swapping the smallest element with the first element of the unsorted part
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
}
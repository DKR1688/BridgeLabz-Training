import java.util.Arrays;

public class RemoveDuplicate {
    public static void main(String[] args) {
        int[] arr ={2, 3, 3, 1, 7, 1, 4};
        int n=arr.length;
        int[] empty= new int[n];
        
        int k=0;
        for (int i=0; i<n; i++) {
            boolean duplicate= false;
            for (int j=0; j<k; j++) {
                if (arr[i]==empty[j]) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate){
                empty[k]=arr[i];
                k++;
            }
        }
        int[] ans= Arrays.copyOf(empty, k);
        System.out.println("without duplicate array is- "+Arrays.toString(ans));
    }
}
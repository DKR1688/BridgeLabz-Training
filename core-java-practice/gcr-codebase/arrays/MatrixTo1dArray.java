//a Java program to create a 2D Array and Copy the 2D Array into a single dimension array
import java.util.Scanner;

public class MatrixTo1dArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of rows and column ");
        int rowsOfMatrix = scanner.nextInt();
        int columnOfMatrix = scanner.nextInt();

        int[][] matrix = new int[rowsOfMatrix][columnOfMatrix];

        System.out.println("Enter numbers of the matrix:");
        for (int i=0; i<rowsOfMatrix; i++) {
            for (int j=0; j<columnOfMatrix; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        // here we will create a array of matrix size
        int[] array = new int[rowsOfMatrix*columnOfMatrix];
        int index = 0;

        // here we print number of 2d array in 1d arry
        for (int i=0; i<rowsOfMatrix; i++) {
            for (int j=0; j<columnOfMatrix; j++) {
                array[index] = matrix[i][j];
                index++;
            }
        }

        System.out.println("1D Array elements:");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }

        scanner.close();
    }
}
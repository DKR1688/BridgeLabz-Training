package methods;

import java.util.*;
public class MatrixManipulationAdvanced {
	public static void main(String[] args) {
        int sizeOf2x2 = 2;
        int sizeOf3x3 = 3;
        int maxValue = 10;

        double[][] matrix2x2 = createRandomMatrix(sizeOf2x2, sizeOf2x2, maxValue);
        System.out.println("2x2 Matrix is- ");
        displayMatrix(matrix2x2);
        System.out.println("Transpose is- ");
        displayMatrix(transposeMatrix(matrix2x2));
        System.out.println("Determinant is- " + determinant2x2(matrix2x2));
        System.out.println("Inverse is- ");
        displayMatrix(inverse2x2(matrix2x2));

        double[][] matrix3x3 = createRandomMatrix(sizeOf3x3, sizeOf3x3, maxValue);
        System.out.println("3x3 Matrix:");
        displayMatrix(matrix3x3);
        System.out.println("Transpose:");
        displayMatrix(transposeMatrix(matrix3x3));
        System.out.println("Determinant: " + determinant3x3(matrix3x3));
        System.out.println("Inverse:");
        displayMatrix(inverse3x3(matrix3x3));
    }

    //a Method to create a random matrix taking rows and columns as parameters
    public static double[][] createRandomMatrix(int rows, int cols, int maxValue) {
        double[][] matrix = new double[rows][cols];
        Random rand = new Random();
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                matrix[i][j] = rand.nextInt(maxValue + 1);
            }
        }
        return matrix;
    }

    //a Method to find transpose of a matrix
    public static double[][] transposeMatrix(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] transpose = new double[cols][rows];
        for (int i=0; i<rows; i++)
            for (int j=0; j<cols; j++)
                transpose[j][i] = matrix[i][j];
        return transpose;
    }

    //a Method to find the determinant of a 2x2 matrix
    public static double determinant2x2(double[][] matrix) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }

    //a Method to find the determinant of a 3x3 matrix
    public static double determinant3x3(double[][] matrix) {
        double det = matrix[0][0]*(matrix[1][1]*matrix[2][2]-matrix[1][2]*matrix[2][1])
                    - matrix[0][1]*(matrix[1][0]*matrix[2][2]-matrix[1][2]*matrix[2][0])
                    + matrix[0][2]*(matrix[1][0]*matrix[2][1]-matrix[1][1]*matrix[2][0]);
        return det;
    }

    // a Method to find the inverse of a 2x2 matrix
    public static double[][] inverse2x2(double[][] matrix) {
        double det = determinant2x2(matrix);
        if (det == 0) {
            System.out.println("2x2 Matrix is singular; cannot find inverse.");
            return null;
        }
        double[][] inv = {
            { matrix[1][1]/det, -matrix[0][1]/det },
            { -matrix[1][0]/det, matrix[0][0]/det }
        };
        return inv;
    }

    //a Method to find the inverse of a 3x3 matrix
    public static double[][] inverse3x3(double[][] matrix) {
        double det = determinant3x3(matrix);
        if (det == 0) {
            System.out.println("3x3 Matrix is singular; cannot find inverse.");
            return null;
        }
        double[][] inv = new double[3][3];
        inv[0][0] = (matrix[1][1]*matrix[2][2]-matrix[1][2]*matrix[2][1])/det;
        inv[0][1] = (matrix[0][2]*matrix[2][1]-matrix[0][1]*matrix[2][2])/det;
        inv[0][2] = (matrix[0][1]*matrix[1][2]-matrix[0][2]*matrix[1][1])/det;
        inv[1][0] = (matrix[1][2]*matrix[2][0]-matrix[1][0]*matrix[2][2])/det;
        inv[1][1] = (matrix[0][0]*matrix[2][2]-matrix[0][2]*matrix[2][0])/det;
        inv[1][2] = (matrix[0][2]*matrix[1][0]-matrix[0][0]*matrix[1][2])/det;
        inv[2][0] = (matrix[1][0]*matrix[2][1]-matrix[1][1]*matrix[2][0])/det;
        inv[2][1] = (matrix[0][1]*matrix[2][0]-matrix[0][0]*matrix[2][1])/det;
        inv[2][2] = (matrix[0][0]*matrix[1][1]-matrix[0][1]*matrix[1][0])/det;
        return inv;
    }
    
    //a Method to display a matrix
    public static void displayMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                System.out.println(val);
            }
            System.out.println();
        }
        System.out.println();
    }  
}
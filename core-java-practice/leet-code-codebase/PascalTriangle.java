import java.util.*;

public class PascalTriangle {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter the number of rows ");
		int numRows =scanner.nextInt();
		
        List<List<Integer>> pascalTriangle = new ArrayList<>();
        for (int i=0; i<numRows; i++) {
            List<Integer> currentRow = new ArrayList<>();
            
            //In pascal first row is always  1
            currentRow.add(1);
            for (int j=1; j<i; j++) {
                List<Integer> prevRow = pascalTriangle.get(i - 1);
                currentRow.add(prevRow.get(j - 1) + prevRow.get(j));
            }
            if (i>0) {
            	
            	//also last element is 1
                currentRow.add(1);
            }
            pascalTriangle.add(currentRow);
        }
        for (List<Integer> row : pascalTriangle) {
            System.out.println(row);
        }
        scanner.close();
    }
}
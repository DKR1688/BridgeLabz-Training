import java.util.*;
public class LineComparisonProblem{
    public static void main(String[] args) {
        System.out.println("Welcome to Line Comparison Computation!");
        Scanner scanner= new Scanner(System.in);

        //UC1 - a line based on a point consisting of (x, y) co-ordinates using the Cartesian system.
        System.out.println("Enter coordinates for a line (x1, y1) and (x2, y2) - ");
        double x1 =scanner.nextDouble();
        double y1 =scanner.nextDouble();
        double x2 =scanner.nextDouble();
        double y2 =scanner.nextDouble();

        Double length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        System.out.printf("Length of a line is %.2f \n",length);
        
        //UC2 - check equality of two lines based on the end points, So that I know when two lines are the equal.
        System.out.println("Enter coordinates for second line (x3, y3) and (x4, y4) - ");
        double x3 =scanner.nextDouble();
        double y3 =scanner.nextDouble();
        double x4 =scanner.nextDouble();
        double y4 =scanner.nextDouble();
        
        Double secondLength = Math.sqrt(Math.pow(x4 - x3, 2) + Math.pow(y4 - y3, 2));        
        System.out.printf("Length of second line %.2f \n",secondLength);
        if(length.equals(secondLength)) {
        	System.out.println("Both lines are equal with same lengths.");
        }else {
        	System.out.println("Both lines are not equal with different lengths.");
        }

        //UC3 - compare two lines based on the end points, So that I know one line is equal, greater or less than the other line.
        int ans =length.compareTo(secondLength);
        System.out.print("Comparison of equality between Line 1 and Line 2: ");
        if (ans>0) {
            System.out.println("Line 1 is equal to Line 2. \n");
        } else if (ans<0) {
            System.out.println("Line 1 is shorter than Line 2.");
        } else {
            System.out.println("Line 1 is longer than Line 2.");
        }
        scanner.close();
    }
}
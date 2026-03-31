package methods;

import java.util.*;
public class CollinearFormulaCheck {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the 3 points ");
        int x1 = scanner.nextInt();
        int y1 = scanner.nextInt();
 
        int x2 = scanner.nextInt();
        int y2 = scanner.nextInt();

        int x3 = scanner.nextInt();
        int y3 = scanner.nextInt();

        if (isCollinearUsingSlope(x1, y1, x2, y2, x3, y3)) {
            System.out.println("Points are Collinear (Slope Method)");
        } else {
            System.out.println("Points are NOT Collinear (Slope Method)");
        }

        if (isCollinearUsingArea(x1, y1, x2, y2, x3, y3)) {
            System.out.println("Points are Collinear (Area Method)");
        } else {
            System.out.println("Points are NOT Collinear (Area Method)");
        }
        scanner.close();
    }

    //a Method to find the 3 points that are collinear using the slope formula. 
	//The 3 points A(x1,y1), b(x2,y2), and c(x3,y3) are collinear if the slopes formed by 3 points ab, bc, and cd are equal. 
    public static boolean isCollinearUsingSlope(
            int x1, int y1, int x2, int y2, int x3, int y3) {

        return (y2 - y1) * (x3 - x2) ==(y3 - y2) * (x2 - x1);
    }

    //method to find the three points is collinear using the area of the triangle formula. 
    //The Three points are collinear if the area of the triangle formed by three points is 0.
    public static boolean isCollinearUsingArea(
            int x1, int y1, int x2, int y2, int x3, int y3) {

        double area = 0.5 * (x1 * (y2 - y3)
                           + x2 * (y3 - y1)
                           + x3 * (y1 - y2));
        return area == 0;
    }
}

import java.util.*;
public class PascalTriangle2 {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        row.add(1);

        for (int i=1; i<=rowIndex; i++) {
        	
            //updating row from end to start to avoid overwriting
            for (int j=row.size()-1; j>0; j--) {
                row.set(j, row.get(j)+row.get(j - 1));
            }
            //last element is always 1
            row.add(1);
        }
        return row;
    }

    public static void main(String[] args) {
        PascalTriangle2 solution = new PascalTriangle2();

        System.out.println(solution.getRow(3));
        System.out.println(solution.getRow(0));
        System.out.println(solution.getRow(1));
    }
}
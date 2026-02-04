import java.util.Scanner;
public class UnitConversionTool {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the Km:");
        double km = in.nextDouble();

        System.out.println("Enter the kilograms:");
        double weight = in.nextDouble();

        System.out.printf("%.2f Km in Miles is %.2f%n",
                km, Convert.conversionKmtoMiles(km));

        System.out.printf("%.2f Kg in LBS is %.2f%n",
                weight, Convert.conversionKgtoLBS(weight));

        in.close();
    }
}

interface Convert {
    static double conversionKmtoMiles(double distance) {
        return distance * 0.621371;
    }

    static double conversionKgtoLBS(double weight) {
        return weight * 2.20462;
    }
}


public class TemperatureAnalyzer{
    public static void main(String[] args) {
        float[][] temps = new float[7][24];

        //Filling with sample random value between 15–40°C
        for (int day=0; day<7; day++) {
            for (int hour = 0; hour < 24; hour++) {
                temps[day][hour] = (float)(Math.random() * 40);
            }
        }

        int hottestDay = 0;
        int coldestDay = 0;
        float hottestAvg = Float.MIN_VALUE;
        float coldestAvg = Float.MAX_VALUE;

        for (int day=0; day < 7; day++) {
            float sum = 0;
            for (int hour=0; hour<24; hour++) {
                sum += temps[day][hour];
            }
            float avg = sum / 24;

            System.out.printf("Day %d average: %.2f°C%n", day + 1, avg);

            if (avg > hottestAvg) {
                hottestAvg = avg;
                hottestDay = day;
            }
            if (avg < coldestAvg) {
                coldestAvg = avg;
                coldestDay = day;
            }
        }

        System.out.println("\nHottest Day is- Day " + (hottestDay + 1) + " with avg " + hottestAvg + "°C");
        System.out.println("Coldest Day is- Day " + (coldestDay + 1) + " with avg " + coldestAvg + "°C");
    }
}

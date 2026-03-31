package StreamBuzz;
import java.util.*;
public class Program {
	public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Program p=new Program();

        boolean exit=true;
        while (exit) {
        	System.out.println();
            System.out.println("1- Register Creator");
            System.out.println("2- Show Top Posts");
            System.out.println("3- Calculate Average Likes");
            System.out.println("4- Exit");
            System.out.print("Enter your choice- ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter creator name- ");
                    String name=sc.nextLine();

                    double[] likes =new double[4];
                    System.out.println("Enter weekly likes- ");
                    for (int i=0; i<4; i++) {
                        likes[i] =sc.nextDouble();
                    }

                    CreatorStats record=new CreatorStats(name, likes);
                    p.registerCreator(record);
                    break;

                case 2:
                    System.out.print("Enter like threshold- ");
                    double threshold =sc.nextDouble();

                    Map<String, Integer> topPosts =p.getTopPostCounts(CreatorStats.engagementBoard, threshold);
                    if (topPosts.isEmpty()) {
                        System.out.println("No top-performing posts this week.");
                    } else {
                        for (Map.Entry<String, Integer> entry : topPosts.entrySet()) {
                            System.out.println(entry.getKey()+" - "+entry.getValue());
                        }
                    }
                    break;

                case 3:
                    double avg =p.CalculateAverageLikes();
                    System.out.println("Overall average weekly likes- "+(int) avg);
                    break;

                case 4:
                    System.out.println("Exitting...");
                    exit =false;
                    break;

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
        sc.close();
    }
	
    public void registerCreator(CreatorStats record) {
        CreatorStats.engagementBoard.add(record);
        System.out.println("Creator registered successfully.");
    }

    // Count weeks with likes >= threshold
    public Map<String, Integer> getTopPostCounts(List<CreatorStats> records, double likeThreshold) {
        Map<String, Integer> result=new LinkedHashMap<>();

        for (CreatorStats cs :records) {
            int count=0;
            for (double likes : cs.weeklyLikes) {
                if (likes >= likeThreshold) {
                    count++;
                }
            }
            if (count>0) {
                result.put(cs.creatorName, count);
            }
        }
        return result;
    }

    public double CalculateAverageLikes() {
        double sum=0;
        int total=0;

        for (CreatorStats cs: CreatorStats.engagementBoard) {
            for (double likes: cs.weeklyLikes) {
                sum+=likes;
                total++;
            }
        }
        return sum/total;
    }
}

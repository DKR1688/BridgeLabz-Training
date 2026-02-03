package StreamBuzz;
import java.util.*;
public class CreatorStats {
	String creatorName;
	double[] weeklyLikes;
	public static List<CreatorStats> engagementBoard= new ArrayList<>();
	
	CreatorStats(String creatorName, double[] weeklyLikes) {
        this.creatorName =creatorName;
        this.weeklyLikes =weeklyLikes;
    }


}

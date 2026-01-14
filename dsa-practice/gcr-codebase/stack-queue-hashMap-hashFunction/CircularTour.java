import java.util.*;

class Petrolpump {
	int petrol;
	int distance;

	Petrolpump(int p, int d) {
		petrol = p;
		distance = d;
	}
}

public class CircularTour {
	public static int checkPossibility(Petrolpump pumps[]) {
		int start = 0;
		int currPetrol = 0;
		Queue<Petrolpump> queue = new LinkedList<>();
		
		for (int i=0; i< pumps.length; i++) {
			queue.offer(pumps[i]);
			currPetrol += pumps[i].petrol - pumps[i].distance;
			
			while (currPetrol<0 && !queue.isEmpty()) {
				Petrolpump removed = queue.poll();
				currPetrol -= (pumps[i].petrol - pumps[i].distance);
				start++;
			}
		}
		return queue.size() == pumps.length ? start : -1;

	}

	public static void main(String[] args) {
		Petrolpump[] pumps = { new Petrolpump(6, 4), new Petrolpump(3, 6), new Petrolpump(7, 3) };

		System.out.println(checkPossibility(pumps));

	}

}


public class RestaurantOrderSystem {
	public static void main(String[] args) {
		//creating chef threads with dishes and times
		Chef chef1 = new Chef("Chef-1", "Pizza", 3000);
		Chef chef2 = new Chef("Chef-2", "Pasta", 2000);
		Chef chef3 = new Chef("Chef-3", "Salad", 1000);
		Chef chef4 = new Chef("Chef-4", "Burger", 2500);

		//starting all chefs
		chef1.start();
		chef2.start();
		chef3.start();
		chef4.start();

		try {
			chef1.join();
			chef2.join();
			chef3.join();
			chef4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Kitchen closed - All orders completed.");
	}
}

class Chef extends Thread {
	String dish;
	int time;

	Chef(String name, String dish, int time) {
		super(name); //setting thread name as Chef name
		this.dish =dish;
		this.time =time;
	}

	@Override
	public void run() {
		System.out.println(getName() + " started preparing " + dish);

		//simulating progress at 25%, 50%, 75%, 100%
		int[] progressMarks={ 25, 50, 75, 100 };
		for (int mark : progressMarks) {
			try {
				Thread.sleep(time/4); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(getName() + " preparing " + dish + "- " + mark + "% complete");
		}

		System.out.println(getName() + " finished preparing " + dish);
	}
}

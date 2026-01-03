
public class RestaurantManagementSystem {
	public static void main(String[] args) {
		Chef chef = new Chef("Deepak", 101);
		Waiter waiter = new Waiter("Rishabh", 201);

		chef.performDuties();
		waiter.performDuties();
	}
}

class Person2 {
	String name;
	int id;

	public Person2(String name, int id) {
		this.name = name;
		this.id = id;
	}
}

//Interface
interface Worker {
	void performDuties();
}

class Chef extends Person2 implements Worker {
	public Chef(String name, int id) {
		super(name, id);
	}

	@Override
	public void performDuties() {
		System.out.println("Chef name is- " + name);
	}
}

class Waiter extends Person2 implements Worker {
	public Waiter(String name, int id) {
		super(name, id);
	}

	@Override
	public void performDuties() {
		System.out.println("Waiter name is- " + name);
	}
}
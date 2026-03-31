public class Person {
	String name;
	int age;
	
	Person(String name, int age){
		this.name =name;
		this.age =age;
	}
	
	//Copy constructor to clones another Person object
	Person(Person other){
		this.name =other.name;
		this.age =other.age;
	}
	
	void display() {
        System.out.println("Name is- "+name+", Age is- "+age);
        System.out.println();
    }

	public static void main(String[] args) {
        Person person1 =new Person("Deepak", 22);
        person1.display();

        // Cloning p1 using copy constructor
        Person person2 =new Person(person1);
        person2.display();
    }
}

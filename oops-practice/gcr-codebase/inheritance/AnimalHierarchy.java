public class AnimalHierarchy {
	public static void main(String[] args) {
        Animal dog=new Dog("Doggy", 7);
        Animal cat=new Cat("Kitti", 5);
        Animal bird=new Bird("Crow", 2);

        dog.makeSound();
        cat.makeSound();
        bird.makeSound();
    }
}

//Superclass
class Animal {
    String name;
    int age;

    Animal(String name, int age) {
        this.name=name;
        this.age=age;
    }

    void makeSound() {
        System.out.println("Animal sound..");
    }
}

//Subclasses
class Dog extends Animal {
	Dog(String name, int age) {
		super(name, age);
	}

	@Override
	void makeSound() {
		System.out.println(name + " sound- Woof Woof!");
	}
}

class Cat extends Animal {
	Cat(String name, int age) {
		super(name, age);
	}

	@Override
	void makeSound() {
		System.out.println(name + " sound- Meow Meow!");
	}
}

class Bird extends Animal {
    Bird(String name, int age) {
        super(name, age);
    }

    @Override
    void makeSound() {
        System.out.println(name + " sound- caa caa!");
    }
}

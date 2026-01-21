import java.util.*;
public class MealPlanGenerator {
	public static void main(String[] args) {
        //vegetarian Plan
        Meal<VegetarianMeal> veg =new Meal<>(new VegetarianMeal());
        veg.generatePlan(new VegetarianMeal());
        System.out.println();

        //vegan Plan
        Meal<VeganMeal> veganMeal = new Meal<>(new VeganMeal());
        veganMeal.generatePlan(new VeganMeal());
        System.out.println();
    }
}

//an interface MealPlan with subtypes (VegetarianMeal, VeganMeal, etc.)
interface MealPlan{
	public void showPlan();
}

class VegetarianMeal implements MealPlan{
	public void showPlan() {
		System.out.println("Vegetarian meal available.");
	}
}

class VeganMeal implements MealPlan {
    public void showPlan() {
        System.out.println("Vegan meal available.");
    }
}

//a generic class Meal<T extends MealPlan> to handle different meal plans.
class Meal<T extends MealPlan> {
    T mealPlan;

    Meal(T mealPlan) {
        this.mealPlan = mealPlan;
    }

    //generic method to validate and generate meal plan
    public <U extends MealPlan> void generatePlan(U plan) {
        System.out.println("Generating your meal plan...");
        plan.showPlan();
    }

    //displaying current meal plan
    public void display() {
        mealPlan.showPlan();
    }
}


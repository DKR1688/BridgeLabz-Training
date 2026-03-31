public class Circle{
    double radius;
    
    //default constructor
    Circle(){
    	this.radius =2;
    }

    //parameterized constructor
    Circle(double radius){
        this.radius= radius;
    }

    //this method is to calculate area
    double area(){
        return Math.PI *radius*radius;
    }

    //this method is to calculate circumference
    double circumference(){
        return 2 * Math.PI * radius;
    }
    
    void display() {
    	System.out.println("Area of the circle is- " + area());
        System.out.println("Circumference of the circle is- " +circumference());
        System.out.println();
    }


    public static void main(String[] args){
    	Circle circle1 =new Circle();
    	circle1.display();
    	
        Circle circle2= new Circle(5);
        circle2.display();  
    }
}
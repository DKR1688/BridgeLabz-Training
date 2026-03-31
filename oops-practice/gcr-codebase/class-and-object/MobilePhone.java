public class MobilePhone {
	String brand;
    String model;
    double price;

    public MobilePhone(String brand, String model, double price) {
        this.brand = brand;
        this.model = model;
        this.price = price;
    }

    //this method is to print info
    public void display() {
        System.out.println("Brand of mobile- " +brand);
        System.out.println("Model of mobile- " +model);
        System.out.println("Price of mobile- " +price);
        System.out.println("---------------------------");
    }
    
    public static void main(String[] args) {
        MobilePhone phone1 = new MobilePhone("VIVO", "VIVO V29", 15999.0);
        MobilePhone phone2 = new MobilePhone("ONE PLUS", "ONE PLUS nord4", 39999.0);
        MobilePhone phone3 = new MobilePhone("APPLE", "iphone pro16", 79999.0);

        phone1.display();
        phone2.display();
        phone3.display();
    }
}

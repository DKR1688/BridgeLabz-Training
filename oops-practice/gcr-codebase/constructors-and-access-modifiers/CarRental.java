public class CarRental {
    String customerName;
    String carModel;
    int rentalDays;
    
    //Default Constructor
    CarRental() {
        this.customerName ="xyz";
        this.carModel ="Economy";
        this.rentalDays =2;
    }
    
    //Parameterized Constructor
    CarRental(String customerName, String carModel, int rentalDays) {
        this.customerName =customerName;
        this.carModel =carModel;
        this.rentalDays =rentalDays;
    }
    
    //Copy Constructor
    CarRental(CarRental other) {
        this.customerName =other.customerName;
        this.carModel =other.carModel;
        this.rentalDays =other.rentalDays;
    }
    
    //this method is to calculate total cost to the rental
    double calculateTotalCost() {
        double rate;
        switch (carModel.toLowerCase()) {
            case "suv":
                rate =2000;
                break;
            case "luxury":
                rate =3000;
                break;
            default:
                rate =1000;
        }
        return rate*rentalDays;
    }

    //this method is to display rental details
    void displayRental() {
        System.out.println("Customer name is- " +customerName+"\n Car model is " + carModel +
                           "\n total days are- " +rentalDays+ "\n Total cost is- " +calculateTotalCost());
        System.out.println();
    }

    public static void main(String[] args) {
        //Using Default Constructor
        CarRental rental1 =new CarRental();
        rental1.displayRental();

        //Using Parameterized Constructor
        CarRental rental2 =new CarRental("Deepak Kumar", "luxury", 5);
        rental2.displayRental();

        //Using Copy Constructor
        CarRental rental3 =new CarRental(rental2);
        rental3.displayRental();
    }
}
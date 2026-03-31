package FutureLogistics;

class BrickTransport extends GoodsTransport {
    private float brickSize;
    private int brickQuantity;
    private float brickPrice;

    public BrickTransport(String transportId, String transportDate, int transportRating, float brickSize, int brickQuantity, float brickPrice) {
        super(transportId, transportDate, transportRating);
        this.brickSize=brickSize;
        this.brickQuantity =brickQuantity;
        this.brickPrice =brickPrice;
    }

    public float getBrickSize() { 
    	return brickSize; 
    }
    public void setBrickSize(float brickSize) {
    	this.brickSize =brickSize;
    }

    public int getBrickQuantity() { 
    	return brickQuantity; 
    }
    public void setBrickQuantity(int brickQuantity) {
    	this.brickQuantity =brickQuantity; 
    }

    public float getBrickPrice() { 
    	return brickPrice; 
    }
    public void setBrickPrice(float brickPrice) { 
    	this.brickPrice=brickPrice;
    }

    @Override
    public String vehicleSelection() {
        if (brickQuantity<300) {
        	return "Truck";
        }else if (brickQuantity <= 500) {
        	return "Lorry";
        }else {
        	return "MonsterLorry";
        }
    }

    @Override
    public float calculateTotalCharge() {
        float price = brickPrice*brickQuantity;
        float tax = price*0.3f;

        //vehicle price
        float vehiclePrice = 0;
        String vehicle=vehicleSelection();
        if (vehicle.equalsIgnoreCase("Truck")) {
        	vehiclePrice = 1000;
        }else if (vehicle.equalsIgnoreCase("Lorry")) {
        	vehiclePrice=1700;
        }else if (vehicle.equalsIgnoreCase("MonsterLorry")) {
        	vehiclePrice=3000;
        }

        //discount based on rating
        float discount=0;
        if (transportRating == 5) {
        	discount = price*0.20f;
        }else if (transportRating == 3 || transportRating == 4) {
        	discount = price *0.10f;
        }
        return (price+vehiclePrice+tax) - discount;
    }
}
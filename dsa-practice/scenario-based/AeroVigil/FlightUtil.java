package AeroVigil;

public class FlightUtil {
	public boolean validateFlightNumber(String flightNumber) throws InvalidFlightException {
        if (flightNumber.matches("FL-[0-9]{4}")) {
            int num =Integer.parseInt(flightNumber.substring(3));
            if (num>=1000 && num<=9999) {
                return true;
            }
        }
        throw new InvalidFlightException("The flight number "+flightNumber+" is invalid.");
    }

	public boolean validateFlightName(String flightName) throws InvalidFlightException {
        if (flightName.equalsIgnoreCase("SpiceJet") || flightName.equalsIgnoreCase("Vistara") ||
            flightName.equalsIgnoreCase("IndiGo") || flightName.equalsIgnoreCase("Air Arabia")) {
            return true;
        }
        throw new InvalidFlightException("The flight name "+flightName+" is invalid.");
    }

	public boolean validatePassengerCount(int passengerCount, String flightName) throws InvalidFlightException {
        int maxCapacity=0;
        switch (flightName) {
            case "SpiceJet": 
            	maxCapacity=396; 
            	break;
            	
            case "Vistara": 
            	maxCapacity=615; 
            	break;
            	
            case "IndiGo": 
            	maxCapacity=230; 
            	break;
            	
            case "Air Arabia": 
            	maxCapacity=130; 
            	break;
        }

        if (passengerCount>0 && passengerCount<=maxCapacity) {
            return true;
        }
        throw new InvalidFlightException("The passenger count "+passengerCount+" is invalid for "+flightName);
    }

	public double calculateFuelToFillTank(String flightName, double currentFuelLevel) throws InvalidFlightException {
        double maxCapacity=0;
        switch (flightName) {
            case "SpiceJet": 
            	maxCapacity =200000; 
            	break;
            	
            case "Vistara": 
            	maxCapacity =300000; 
            	break;
            	
            case "IndiGo": 
            	maxCapacity =250000; 
            	break;
            	
            case "Air Arabia": 
            	maxCapacity =150000; 
            	break;
        }

        if (currentFuelLevel<0 || currentFuelLevel>maxCapacity) {
            throw new InvalidFlightException("Invalid fuel level for "+flightName);
        }
        return maxCapacity-currentFuelLevel;
	}
}

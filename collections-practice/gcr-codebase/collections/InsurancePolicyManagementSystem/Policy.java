package InsurancePolicyManagementSystem;
import java.time.LocalDate;
public class Policy implements Comparable<Policy>{
	String number;
	String name;
	LocalDate expiryDate;
	String coverageType;
	int amount;
	
	Policy(String number, String name, LocalDate expiryDate, String coverageType, int amount){
		this.number =number;
		this.name =name;
		this.expiryDate =expiryDate;
		this.coverageType =coverageType;
		this.amount =amount;
	}
	
	@Override
	public int compareTo(Policy other) {
		return this.expiryDate.compareTo(other.expiryDate);
	}
	
	@Override
	public String toString() {
	    return "Policy details are- {" +
	            "number is- " + number+
	            ", name is- " + name+
	            ", expiryDate is- " + expiryDate+
	            ", coverageType is- " + coverageType+
	            ", amount is- " +amount+"}";
	}
}

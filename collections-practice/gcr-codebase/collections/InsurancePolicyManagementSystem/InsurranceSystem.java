package InsurancePolicyManagementSystem;
import java.time.*;
import java.util.Arrays;
public class InsurranceSystem {
	public static void main(String[] args) {
		Policy p1=new Policy("Policy1", "Deepak", LocalDate.now().plusDays(10), "Health", 50000);
		Policy p2=new Policy("Policy2", "Abhay", LocalDate.now().plusDays(40), "Home", 30000);
		Policy p3=new Policy("Policy3", "Abhishek", LocalDate.now().plusDays(15), "Health", 50000);
		
		PolicyManagement manage=new PolicyManagement();
		
		manage.addPolicy(p1);
		manage.addPolicy(p2);
		manage.addPolicy(p3);
		
		manage.retrievePolicy();
		manage.expiringSoon();
		manage.coverageType("Health");
		
		manage.checkDuplicates(Arrays.asList(p1, p2, p3));
		manage.performance();
	}
}

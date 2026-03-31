package M1_Set3;

import java.util.*;
public class EmailAccessControl {
	public boolean validateEmail(String email) {
		if(!email.matches("^[a-z]{3,}\\.[a-z]{3,}[0-9]{4}@(sales|marketing|IT|product)\\.company.com$")) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int N=sc.nextInt();
		sc.nextLine();
		
		List<String> emails=new ArrayList<>();
		for(int i=0; i<N; i++) {
			emails.add(sc.nextLine());
		}
		
		EmailAccessControl e=new EmailAccessControl();
		for(int i=0; i<N; i++) {
			String email=emails.get(i);
			if(e.validateEmail(email)) {
				System.out.println("Access Granted");
			}else {
				System.out.println("Access Denied");
			}
		}
	}
}

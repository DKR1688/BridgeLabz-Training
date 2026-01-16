package AddressBookSystem;

public class AddressBookMain {
	public static void main(String[] args) {
		System.out.println("Welcome to Address Book...");
		System.out.println();
		
		//UC1
		Contacts contact =new Contacts("Deepak", "Rajput", "GLA College", "Mathura", 
							"Uatter pradesh", 281006, 9045451688L, "deepak.rajput_cs22@gla.ac.in");
		System.out.println(contact);
	}
}

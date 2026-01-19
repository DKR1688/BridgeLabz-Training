// Use case 1: Ability to create a Contacts in Address Book with first and last names, 
//address, city, state, zip, phone number and email...
package AddressBookSystem;

import java.util.Objects;

class Contact {
	String firstName;
	String lastName;
	String address;
	String city;
	String state;
	int zip;
	long phoneNumber;
	String email;
	
	public Contact(String firstName, String lastName, String address, String city,
			String state, int zip, long  phoneNumber, String email) {
		this.firstName =firstName;
		this.lastName =lastName;
		this.address =address;
		this.city =city;
		this.state =state;
		this.zip =zip;
		this.phoneNumber =phoneNumber;
		this.email =email;
	}

//	//we will override toString is to display details
//	@Override
//	public String toString() {
//		return "Contact details of user are- "+ "\nFirst name is- "+firstName+ "\n Last name is- "+lastName+ 
//				"\n Address is- "+address+ "\n City is- "+city+ "\n State is- "+state+ "\n Zip code is- "+zip+ 
//				"\n Phone number is- "+phoneNumber+ "\n Email is- "+email;
//	}
	
	//Use case 7- Override equals method to check for Duplicate
	@Override
	public boolean equals(Object obj) {
		if(this==obj) {
			return true;
		}
		if(obj==null) {
			return false;
		}
		Contact contact =(Contact) obj;
		return firstName.equalsIgnoreCase(contact.firstName) &&
	           lastName.equalsIgnoreCase(contact.lastName);
	}
	@Override
	public int hashCode() {
		return Objects.hash(firstName.toLowerCase(), lastName.toLowerCase());
	}
	
	
	public void displayDetails() {
		System.out.println("Contact details of user are- ");
		System.out.println("First name is- "+firstName);
		System.out.println("Last name is- "+lastName);
		System.out.println("Address is- "+address);
		System.out.println("City is- "+city);
		System.out.println("State is- "+state);
		System.out.println("Zip code is- "+zip);
		System.out.println("Phone number is- "+phoneNumber);
		System.out.println("Email is- "+email);
	}
	
}

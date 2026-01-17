//Use case 2: Ability to add a new Contact to Address Book
package AddressBookSystem;

import java.util.ArrayList;
import java.util.Scanner;
class AddressBook {
	ArrayList<Contact> contacts;
	
	public AddressBook() {
		contacts=new ArrayList<>();
	}
	
	//we add new contact details
	public void addContact(Contact contact) {
		System.out.println();
		contacts.add(contact);
		System.out.println("Details added.");
	}
	
	public void displayContacts() {
		if(contacts.isEmpty()) {
			System.out.println("Contect is unavilable in address book.");
		}else {
			System.out.println("Contact are- ");
			for(Contact c: contacts) {
				c.displayDetails();
			}
		}
	}
	
	//Use case 3: Ability to edit existing contact person using their name
	public void editContact(String firstName, String lastName, Scanner sc) {
		boolean found =false;
		for(Contact c: contacts) {
			if(c.firstName.equalsIgnoreCase(firstName) && c.lastName.equalsIgnoreCase(lastName)) {
				found =true;
				System.out.println("Editing contact details of "+firstName+" "+lastName+" --- ");
				System.out.println("Enter new first name- ");
				c.firstName =sc.nextLine();
				
				System.out.println("Enter new last name- ");
				c.lastName =sc.nextLine();
				
				System.out.print("Enter address to edit- ");
                c.address =sc.nextLine();

                System.out.print("Enter city to edit- ");
                c.city =sc.nextLine();

                System.out.print("Enter state to edit- ");
                c.state =sc.nextLine();

                System.out.print("Enter zip to edit- ");
                c.zip =sc.nextInt();
                sc.nextLine();

                System.out.print("Enter phone number to edit- ");
                c.phoneNumber =sc.nextLong();
                sc.nextLine();

                System.out.print("Enter email to edit- ");
                c.email =sc.nextLine();

                System.out.println("Contact edited successfully.");
                break;
			}
		}if(!found) {
			System.out.println("Contact "+firstName+" "+lastName+" not found.");
		}
	}
}

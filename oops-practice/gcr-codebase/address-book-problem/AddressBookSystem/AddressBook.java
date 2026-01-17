//Use case 2: Ability to add a new Contact to Address Book
package AddressBookSystem;

import java.util.ArrayList;
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
}

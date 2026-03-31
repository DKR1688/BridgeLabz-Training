import java.util.*;
// Custom exception for invalid phone numbers
class InvalidPhoneNumberException extends Exception {
    public InvalidPhoneNumberException(String messege) {
        super(messege);
    }
}

public class PhoneContactOrganizer {
    public static void main(String[] args) {
        Contact contact = new Contact();

        try {
            contact.addContact("Deepak Rajput", "9045451688");
            contact.addContact("Abhay Rajput", "9998887776");
            contact.addContact("Priyank", "9636000032");
            System.out.println();

            contact.addContact("Rajput", "9045451688");
            contact.addContact("Invalid User", "12345");
            System.out.println();
            
        } catch (InvalidPhoneNumberException e) {
            System.out.println("Error is- " + e.getMessage());
            System.out.println();
        }
        
        contact.searchContact("9045451688");
        contact.deleteContact("9636000032");
        System.out.println();
        contact.showAll();
    }
}

//contact class
class Contact {
    String name;
    String phoneNumber;

    Contact(String name, String phoneNumber) {
        this.name =name;
        this.phoneNumber =phoneNumber;
    }

    public Contact() {
		// TODO Auto-generated constructor stub
	}

	@Override
    public String toString() {
        return name + " - " + phoneNumber;
    }
    
    //validating phone number
    ArrayList<Contact> contacts = new ArrayList<>();
    private void validatePhone(String phoneNumber) throws InvalidPhoneNumberException {
        if (phoneNumber==null || phoneNumber.length()!=10) {
            throw new InvalidPhoneNumberException("Phone must be exactly 10 digits.");
        }
    }

    //adding contact to the list
    public void addContact(String name, String phoneNumber) throws InvalidPhoneNumberException {
        validatePhone(phoneNumber);
        
        for (Contact contact : contacts) {
            if (contact.phoneNumber.equals(phoneNumber)) {
                System.out.println("Duplicate phone number.");
                return;
            }
        }
        contacts.add(new Contact(name, phoneNumber));
        System.out.println("Added contact is- " +name);
    }

    //deleting contact by using number
    public void deleteContact(String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.phoneNumber.equals(phoneNumber)) {
                contacts.remove(contact);
                System.out.println("Deleted contact is- " +contact.name);
                return;
            }
        }
        System.out.println("No contact found with phone: " + phoneNumber);
    }

    //searching contact using phone number
    public void searchContact(String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.phoneNumber.equals(phoneNumber)) {
                System.out.println("Your searched contact is- " + contact);
                return;
            }
        }
        System.out.println("Not found.");
    }

    //printing all contacts of list
    public void showAll() {
        System.out.println("All contacts are- ");
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }
}
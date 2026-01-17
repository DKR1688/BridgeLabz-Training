package AddressBookSystem;

import java.util.*;
public class AddressBookMain {
	public static void main(String[] args) {
		System.out.println("Welcome to Address Book...");
		System.out.println();
		
//		//UC1
//		Contact contact =new Contact("Deepak", "Rajput", "GLA College", "Mathura", 
//							"Uatter pradesh", 281006, 9045451688L, "deepak.rajput_cs22@gla.ac.in");
//		System.out.println(contact);
		
		//UC2
		Scanner sc=new Scanner(System.in);
		AddressBook addressBook =new AddressBook();
		
		boolean exit= false;
		while(!exit) {
			System.out.println("Address book menu --- ");
			System.out.println("1 - Add new contact");
			System.out.println("2 - View all contacts");
			System.out.println("3 - Edit existing contact by name");
			System.out.println("4 - Exit from Address Book");
			
			System.out.println("Enter your choice- ");
			int choice =sc.nextInt();
			sc.nextLine(); //we need to consume the newline after reading numeric values before calling nextLine() again.

			
			switch(choice) {
			case 1:
				//here we will take input for new contact
                System.out.print("Enter first name- ");
                String firstName =sc.nextLine();

                System.out.print("Enter last name- ");
                String lastName =sc.nextLine();

                System.out.print("Enter address- ");
                String address =sc.nextLine();

                System.out.print("Enter city- ");
                String city =sc.nextLine();

                System.out.print("Enter state- ");
                String state =sc.nextLine();

                System.out.print("Enter zip- ");
                int zip =sc.nextInt();
                sc.nextLine();

                System.out.print("Enter phone number- ");
                long phoneNumber =sc.nextLong();
                sc.nextLine();

                System.out.print("Enter email- ");
                String email =sc.nextLine();

                //adding contact
                Contact contact =new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
                addressBook.addContact(contact);
                break;
                
		   case 2:
				addressBook.displayContacts();
				break;
				
		   case 3:
			   System.out.println("Enter first name and last name of contact to edit- ");
			   String nameFirst =sc.nextLine();
			   String nameLast =sc.nextLine();
			   addressBook.editContact(nameFirst, nameLast, sc);
			   break;
			   
		   case 4:
			   exit =true;
			   System.out.println("Exiting from address book.");
			   break;
			   
		   default:
		      System.out.println("Your choice is invalid.");
			}
		}
		sc.close();
	}
}

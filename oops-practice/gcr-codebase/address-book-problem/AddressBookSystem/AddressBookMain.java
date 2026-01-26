package AddressBookSystem;

import java.util.*;
public class AddressBookMain {
	public static void main(String[] args) {
		System.out.println("Welcome to Address Book Library..");
		System.out.println();
		
//		//UC1
//		Contact contact =new Contact("Deepak", "Rajput", "GLA College", "Mathura", 
//							"Uatter pradesh", 281006, 9045451688L, "deepak.rajput_cs22@gla.ac.in");
//		System.out.println(contact);
		
		//UC2
		Scanner sc=new Scanner(System.in);
		AddressBook addressBook =new AddressBook();
		
		//We will use mapping to multiple address book
		Map<String, AddressBook> addressBooks =new HashMap<>();
		
		boolean exit= false;
		while(!exit) {
			System.out.println("Address book system menu --- ");
			System.out.println("1 - Add new address book");
			System.out.println("2 - Find address book by name");
			System.out.println("3 - View all address book");
			System.out.println("4 - Exit from system");
			
			System.out.println("Enter your choice- ");
			int choice =sc.nextInt();
			sc.nextLine();
			
			switch(choice) {
			case 1:
				System.out.println("Enter name for new address book- ");
				String newAddressBook =sc.nextLine();
				
				if(addressBooks.containsKey(newAddressBook)) {
					System.out.println("This address book already exists.");
				}else {
					addressBooks.put(newAddressBook, new AddressBook());
					System.out.println("New address book added successfully.");
				}
				break;
				
			case 2:
				System.out.println("Enter name to select address book- ");
				String addessBookName =sc.nextLine();
				AddressBook SelectedAddressBook =addressBooks.get(addessBookName);
				
				if(SelectedAddressBook != null) {
					boolean returned =false;
					while(!returned){
						System.out.println("Address book details menu --- ");
						System.out.println("1 - Add new contact");
						System.out.println("2 - View all contacts");
						System.out.println("3 - Edit existing contact by name");
						System.out.println("4 - Delete contact by name");
						System.out.println("5 - Add multiple contact");
						System.out.println("6 - Search person by city or state");
						System.out.println("7 - View persons by city or state");
						System.out.println("8 - Returned to address book system");
						
						System.out.println("Enter your choice- ");
						int subChoice =sc.nextInt();
						sc.nextLine(); //we need to consume the newline after reading numeric values before calling nextLine() again.
						
						switch(subChoice) {
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
						    System.out.println("Enter first name and last name of contact to delete- ");
						    String delFirst = sc.nextLine();
						    String delLast = sc.nextLine();
						    addressBook.deleteContact(delFirst, delLast);
						    break;
					   case 5:
						   addressBook.addMultipleContact(sc);
						   break;
						   
					   case 6:
						   System.out.println("Enter city or state to find contact- ");
						   String searchCity=sc.nextLine();
						   String searchState=sc.nextLine();
						   addressBook.searchByCityOrState(searchCity, searchState);
						   break;
						   
					  case 7:
						    System.out.println("Search by:");
						    System.out.println("1. City");
						    System.out.println("2. State");
						    int searchChoice =sc.nextInt();
						    sc.nextLine();

						    if (searchChoice==1) {
						        System.out.println("Enter city to view persons- ");
						        String personCity =sc.nextLine();
						        addressBook.viewPersonsByCity(personCity);
						    } else if (searchChoice==2) {
						        System.out.println("Enter state to view persons- ");
						        String personState =sc.nextLine();
						        addressBook.viewPersonsByState(personState);
						    } else {
						        System.out.println("Invalid choice. Please select 1 or 2.");
						    }
						    break;
						    
					  case 8:
						   returned =true;
						   System.out.println("Returned to address book system menu successfully.");
						   break;
						    
					   default:
					      System.out.println("Your sub choice is invalid.");
						}
					}
				}
				break;
				
			case 3:
				if(addressBooks.isEmpty()) {
					System.out.println("No address books exists.");
				}else {
					for(String name: addressBooks.keySet()) {
						System.out.println("Available address book is- "+name);
					}
				}
				break;
			
			case 4:
				exit =true;
				System.out.println("Exited from address book system.");
				break;
				
			default:
				System.out.println("Your choice is invalid");
			}
		}
		sc.close();
	}
}

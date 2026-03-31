//Use case 2: Ability to add a new Contact to Address Book
package AddressBookSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
class AddressBook {
	ArrayList<Contact> contacts;
	
	public AddressBook() {
		contacts=new ArrayList<>();
	}
	
	//we add new contact details
	//Use case 7- Duplicate Check is done on Person Name while adding person to Address Book.
	HashMap<String, ArrayList<Contact>> cityDictionary=new HashMap<>();
	HashMap<String, ArrayList<Contact>> stateDictionary=new HashMap<>();
	public void addContact(Contact contact) {
		if (contacts.contains(contact)) {
	        System.out.println("Contact is duplicate, Contact already exists- "+contact.firstName + " " +contact.lastName);
	    } else {
	        contacts.add(contact);
	        System.out.println("Details added successfully.");
	        
	        //uc9 to add dictionary
	        cityDictionary.computeIfAbsent(contact.city, k -> new ArrayList<>()).add(contact);
	        stateDictionary.computeIfAbsent(contact.city, k -> new ArrayList<>()).add(contact);
	    }
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
	
	//Use case 3- Ability to edit existing contact person using their name
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
	
	//Use case 4- Deleting a contact by name
	public void deleteContact(String firstName, String lastName) {
        boolean deleted =false;
        for (int i=0; i<contacts.size(); i++) {
            Contact c =contacts.get(i);
            if (c.firstName.equalsIgnoreCase(firstName) && c.lastName.equalsIgnoreCase(lastName)) {
                contacts.remove(i);
                deleted =true;
                System.out.println("Contact "+firstName + " " +lastName+ " deleted successfully.");
                break;
            }
        }
        if (!deleted) {
            System.out.println("Contact "+firstName+ " "+lastName + " not found.");
        }
    }

	//Use case 5- method to add multiple contact
	public void addMultipleContact(Scanner sc) {
		System.out.println("Number of contacts, which do you want to add- ");
		int num =sc.nextInt();
		sc.nextLine();
		
		for (int i=0; i<num; i++) {
            System.out.println("Enter details for contact "+(i+1)+ " - ");

            System.out.print("Enter first name- ");
            String firstName =sc.nextLine();

            System.out.print("Enter last name- ");
            String lastName =sc.nextLine();

            System.out.print("Enter address- ");
            String address =sc.nextLine();

            System.out.print("Enter city- ");
            String city =sc.nextLine();

            System.out.print("Enter state- ");
            String state = sc.nextLine();

            System.out.print("Enter zip- ");
            int zip =sc.nextInt();
            sc.nextLine();

            System.out.print("Enter phone number- ");
            long phoneNumber =sc.nextLong();
            sc.nextLine();

            System.out.print("Enter email- ");
            String email =sc.nextLine();

            Contact contact =new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
            addContact(contact);
        }
	}
	
	//Use case 8- Searching by city and state 
	public void searchByCityOrState(String city, String state) {
	    boolean found=false;
	    for (Contact c :contacts) {
	        if (c.city.equalsIgnoreCase(city) || c.state.equalsIgnoreCase(state)) {
	            c.displayDetails();
	            found=true;
	        }
	    }
	    if (!found) {
	        System.out.println("No contacts found by city or state- "+city+" or "+state);
	    }
	}

	//Use case 9- view persons by city or state using dictionaries
	public void viewPersonsByCity(String city) {
	    ArrayList<Contact> persons=cityDictionary.get(city);
	    if (persons == null || persons.isEmpty()) {
	        System.out.println("No persons found in city- "+city);
	    } else {
	        System.out.println("Persons in city "+city + " are- ");
	        for (Contact c :persons) {
	            System.out.println(c.firstName+" " +c.lastName);
	        }
	    }
	}

	public void viewPersonsByState(String state) {
	    ArrayList<Contact> persons=stateDictionary.get(state);
	    if (persons == null || persons.isEmpty()) {
	        System.out.println("No persons found in state- "+state);
	    } else {
	        System.out.println("Persons in state "+state+" are- ");
	        for (Contact c :persons) {
	            System.out.println(c.firstName+ " " +c.lastName);
	        }
	    }
	}

	//Use case 10- count persons by city or state
	public void countByCity(String city) {
	    ArrayList<Contact> persons =cityDictionary.get(city);
	    if (persons == null || persons.isEmpty()) {
	        System.out.println("No persons found in city- "+city);
	    } else {
	        System.out.println("Number of persons in city " + city+" - "+persons.size());
	    }
	}

	public void countByState(String state) {
	    ArrayList<Contact> persons =stateDictionary.get(state);
	    if (persons == null || persons.isEmpty()) {
	        System.out.println("No persons found in state- "+state);
	    } else {
	        System.out.println("Number of persons in state "+ state+ " - "+persons.size());
	    }
	}
	
	//Use case 11- sorting contacts by name
	public void sortContactsByName() {
	    if (contacts.isEmpty()) {
	        System.out.println("No contacts available to sort.");
	        return;
	    }

	    //we sort using comparator first by firstName and then by lastName
	    Collections.sort(contacts, new Comparator<Contact>() {
	        @Override
	        public int compare(Contact c1, Contact c2) {
	            int firstCompare =c1.firstName.compareToIgnoreCase(c2.firstName);
	            if (firstCompare!=0) {
	                return firstCompare;
	            }
	            return c1.lastName.compareToIgnoreCase(c2.lastName);
	        }
	    });

	    System.out.println("Contacts sorted alphabetically by name- ");
	    for (Contact c :contacts) {
	        System.out.println(c);
	    }
	}
	
	// Use case 12 - Sorting contacts by City, State, or Zip using Java 8 features
	public void sortContactsByCity() {
	    if (contacts.isEmpty()) {
	        System.out.println("No contacts available to sort by city.");
	        return;
	    }
	    contacts.stream().sorted(Comparator.comparing(Contact::getCity, String.CASE_INSENSITIVE_ORDER)).forEach(System.out::println);
	}

	public void sortContactsByState() {
	    if (contacts.isEmpty()) {
	        System.out.println("No contacts available to sort by state.");
	        return;
	    }
	    contacts.stream().sorted(Comparator.comparing(Contact::getState, String.CASE_INSENSITIVE_ORDER)).forEach(System.out::println);
	}

	public void sortContactsByZip() {
	    if (contacts.isEmpty()) {
	        System.out.println("No contacts available to sort by zip.");
	        return;
	    }
	    contacts.stream().sorted(Comparator.comparingInt(Contact::getZip)).forEach(System.out::println);
	}
	
	// UC 13 - Write contacts to a file
	public void writeContactsToFile(String filename) {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
	        for (Contact c : contacts) {
	            writer.println(c.firstName + "," + c.lastName + "," + c.address + "," +
	                           c.city + "," + c.state + "," + c.zip + "," +
	                           c.phoneNumber + "," + c.email);
	        }
	        System.out.println("Contacts written successfully to file: " + filename);
	    } catch (IOException e) {
	        System.out.println("Error writing to file: " + e.getMessage());
	    }
	}

	// UC 13 - Read contacts from a file
	public void readContactsFromFile(String filename) {
	    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] data = line.split(",");
	            if (data.length == 8) {
	                Contact contact = new Contact(
	                    data[0], data[1], data[2], data[3],
	                    data[4], Integer.parseInt(data[5]),
	                    Long.parseLong(data[6]), data[7]
	                );
	                addContact(contact);
	            }
	        }
	        System.out.println("Contacts read successfully from file: " + filename);
	    } catch (IOException e) {
	        System.out.println("Error reading from file: " + e.getMessage());
	    }
	}


}

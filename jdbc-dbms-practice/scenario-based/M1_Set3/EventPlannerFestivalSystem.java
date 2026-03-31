package M1_Set3;

import java.util.*;

abstract class Festival {
	String name;
	String location;
	String date;

	public Festival(String name, String location, String date) {
		this.name = name;
		this.location = location;
		this.date = date;
	}

	public abstract void displayDetails();
}

class Music extends Festival {
	String headliner;
	String genre;
	int price;

	public Music(String name, String location, String date, String headliner, String genre, int price) {
		super(name, location, date);
		this.headliner = headliner;
		this.genre = genre;
		this.price = price;
	}

	@Override
	public void displayDetails() {
		System.out.println("Festival Name: " + name);
		System.out.println("Location: " + location);
		System.out.println("Date: " + date);
		System.out.println("Headliner: " + headliner);
		System.out.println("Music Genre: " + genre);
		System.out.println("Ticket Price: " + price);
	}
}

class Food extends Festival {
	String cuisine;
	int numStalls;
	double entryFee;

	public Food(String name, String location, String date, String cuisine, int numStalls, double entryFee) {
		super(name, location, date);
		this.cuisine = cuisine;
		this.numStalls = numStalls;
		this.entryFee = entryFee;
	}

	@Override
	public void displayDetails() {
		System.out.println("Festival Name: " + name);
		System.out.println("Location: " + location);
		System.out.println("Date: " + date);
		System.out.println("Cuisine: " + cuisine);
		System.out.println("Number of Stalls: " + numStalls);
		System.out.println("Entry Fee: " + (int) entryFee);
	}
}

class Art extends Festival {
	String artType;
	int numArtists;
	double exhibitionFee;

	public Art(String name, String location, String date, String artType, int numArtists, double exhibitionFee) {
		super(name, location, date);
		this.artType = artType;
		this.numArtists = numArtists;
		this.exhibitionFee = exhibitionFee;
	}

	@Override
	public void displayDetails() {
		System.out.println("Festival Name: " + name);
		System.out.println("Location: " + location);
		System.out.println("Date: " + date);
		System.out.println("Art Type: " + artType);
		System.out.println("Number of Artists: " + numArtists);
		System.out.println("Exhibition Fee: " + (int) exhibitionFee);
	}
}

public class EventPlannerFestivalSystem {
	static Map<String, Festival> festivals = new HashMap<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (sc.hasNextLine()) {
			String input = sc.nextLine().trim();
			String[] parts = input.split(" ");
			String op = parts[0];

			switch (op) {
			case "ADD_FESTIVAL":
				String type = parts[1];
				switch (type) {
				case "MUSIC":
					festivals.put(parts[2],
							new Music(parts[2], parts[3], parts[4], parts[5], parts[6], Integer.parseInt(parts[7])));
					break;

				case "FOOD":
					festivals.put(parts[2], new Food(parts[2], parts[3], parts[4], parts[5], Integer.parseInt(parts[6]),
							Double.parseDouble(parts[7])));
					break;

				case "ART":
					festivals.put(parts[2], new Art(parts[2], parts[3], parts[4], parts[5], Integer.parseInt(parts[6]),
							Double.parseDouble(parts[7])));
					break;

				default:
					System.out.println("festival type is invalid");
				}
				break;

			case "DISPLAY_DETAILS":
				Festival f = festivals.get(parts[1]);
				if (f != null) {
					f.displayDetails();
				} else {
					System.out.println("Festival not found.");
				}
				break;

			default:
				System.out.println("Unknown operation.");
			}
		}

		sc.close();
	}
}
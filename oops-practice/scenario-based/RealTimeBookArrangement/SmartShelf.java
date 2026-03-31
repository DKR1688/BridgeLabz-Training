package RealTimeBookArrangement;

import java.util.*;
public class SmartShelf{
    public static void main(String[] args) {
        Shelf shelf =new Shelf();

        shelf.addBook("Data Structures");
        shelf.addBook("DBMS");
        shelf.addBook("OS");
        shelf.addBook("CN");

        shelf.printShelf();
    }
}

class Shelf {
    ArrayList<String> books;

    public Shelf() {
        books =new ArrayList<>();
    }

    //adding book and keep list sorted using insertion sort
    public void addBook(String title) {
        books.add(title);
        
        insertionSort();
        System.out.println("Book " +title+" added to shelf.");
    }

    //insertion sort to arrange books alphabetically
    private void insertionSort() {
        for (int i=1; i<books.size(); i++) {
            String key = books.get(i);
            int j = i-1;

            //comparing alphabetically
            while (j>=0 && books.get(j).compareToIgnoreCase(key)>0) {
                books.set(j+1, books.get(j));
                j--;
            }
            books.set(j+1, key);
        }
    }

    public void printShelf() {
        if (books.isEmpty()) {
            System.out.println("Shelf is empty.");
            return;
        }
        System.out.println("Current Shelf is- ");
        for (String book :books) {
            System.out.println(book);
        }
    }
}

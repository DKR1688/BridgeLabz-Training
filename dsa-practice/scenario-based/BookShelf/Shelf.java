package BookShelf;

import java.util.*;

class Shelf{
	 //LinkedList of Books
	 Map<String, LinkedList<Book>> catalog;
	 //HashSet to avoid duplicates
	 Set<Book> allBooks;

	 Shelf() {
	     catalog = new HashMap<>();
	     allBooks = new HashSet<>();
	 }

	 //Add a book to a linked list
	 public void addBook(String genre, Book book) {
	     if (allBooks.contains(book)) {
	         System.out.println("Duplicate book not allowed: " + book);
	         return;
	     }

	     catalog.putIfAbsent(genre, new LinkedList<>());
	     catalog.get(genre).add(book);
	     allBooks.add(book);

	     System.out.println("Added " + book + " to genre: " + genre);
	 }

	 //remove from linked list
	 public void borrowBook(String genre, Book book) {
	     LinkedList<Book> books = catalog.get(genre);
	     if (books != null && books.remove(book)) {
	         System.out.println("Borrowed " + book + " from genre: " + genre);
	     } else {
	         System.out.println("Book not found in genre: " + genre);
	     }
	 }

	 //Return a book to linked list
	 public void returnBook(String genre, Book book) {
	     catalog.putIfAbsent(genre, new LinkedList<>());
	     catalog.get(genre).add(book);
	     System.out.println("Returned " + book + " to genre: " + genre);
	 }

	 public void showCatalog() {
	     System.out.println("Library Catalog- ");
	     for (Map.Entry<String, LinkedList<Book>> entry : catalog.entrySet()) {
	         System.out.println("Genre: " + entry.getKey());
	         for (Book b : entry.getValue()) {
	             System.out.println("   " + b);
	         }
	     }
	 }
}
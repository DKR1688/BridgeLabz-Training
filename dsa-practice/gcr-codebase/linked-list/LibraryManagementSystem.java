package LinkedList;
//Doubly Linked List program for Library system

public class LibraryManagementSystem {
	public static void main(String[] args) {
     Library lib = new Library();

     System.out.println("Adding some books into library...\n");

     lib.addFirst("Java Basics", "James Gosling", "Education", "B01", true);
     lib.addLast("C Programming", "Dennis Ritchie", "Education", "B02", true);
     lib.addLast("Harry Potter", "J K Rowling", "Fiction", "B03", false);
     lib.addAt(1, "Python Book", "Guido", "Education", "B04", true);

     System.out.println("All books in library (forward)- ");
     lib.showForward();

     System.out.println("\nAll books in library (reverse)- ");
     lib.showReverse();

     lib.updateStatus("B03", true);
     System.out.println("\nAfter changing availability of Harry Potter- ");
     lib.showForward();

     lib.removeBook("B04");
     System.out.println("\nAfter removing book id B04- ");
     lib.showForward();

     System.out.println("\nSearching book by title Java Basics...");
     System.out.println("Author name is- " + lib.searchByTitle("Java Basics").author);

     System.out.println("\nSearching book by author Dennis Ritchie...");
     System.out.println("Book title is- " + lib.searchByAuthor("Dennis Ritchie").title);

     System.out.println("\nTotal books present in library are- " + lib.countBooks());
 }
}

//Node class for storing book info
class BookNode {
 String title;
 String author;
 String genre;
 String bookId;
 boolean available;
 BookNode next;
 BookNode prev;

 // constructor of book node
 BookNode(String t, String a, String g, String id, boolean av) {
     title = t;
     author = a;
     genre = g;
     bookId = id;
     available = av;
     next = null;
     prev = null;
 }
}

//Library class do all operations
class Library {
 BookNode head;
 BookNode tail;

 // add book at starting
 void addFirst(String t, String a, String g, String id, boolean av) {
     BookNode n = new BookNode(t, a, g, id, av);

     if (head == null) {
         head = tail = n;
         return;
     }

     n.next = head;
     head.prev = n;
     head = n;
 }

 // add book at end
 void addLast(String t, String a, String g, String id, boolean av) {
     BookNode n = new BookNode(t, a, g, id, av);

     if (head == null) {
         head = tail = n;
         return;
     }

     tail.next = n;
     n.prev = tail;
     tail = n;
 }

 // add book at given position
 void addAt(int pos, String t, String a, String g, String id, boolean av) {
     if (pos == 0) {
         addFirst(t, a, g, id, av);
         return;
     }

     BookNode temp = head;
     for (int i = 0; i < pos - 1 && temp != null; i++) {
         temp = temp.next;
     }

     if (temp == null || temp.next == null) {
         addLast(t, a, g, id, av);
         return;
     }

     BookNode n = new BookNode(t, a, g, id, av);
     n.next = temp.next;
     n.prev = temp;
     temp.next.prev = n;
     temp.next = n;
 }

 // remove book using id
 void removeBook(String id) {
     BookNode temp = head;

     while (temp != null) {
         if (temp.bookId.equals(id)) {

             if (temp == head) {
                 head = head.next;
                 if (head != null)
                     head.prev = null;
             } else if (temp == tail) {
                 tail = tail.prev;
                 tail.next = null;
             } else {
                 temp.prev.next = temp.next;
                 temp.next.prev = temp.prev;
             }
             return;
         }
         temp = temp.next;
     }
 }

 // search book by title
 BookNode searchByTitle(String t) {
     BookNode temp = head;
     while (temp != null) {
         if (temp.title.equalsIgnoreCase(t))
             return temp;
         temp = temp.next;
     }
     return null;
 }

 // search book by author
 BookNode searchByAuthor(String a) {
     BookNode temp = head;
     while (temp != null) {
         if (temp.author.equalsIgnoreCase(a))
             return temp;
         temp = temp.next;
     }
     return null;
 }

 // update availability status
 void updateStatus(String id, boolean st) {
     BookNode temp = head;
     while (temp != null) {
         if (temp.bookId.equals(id)) {
             temp.available = st;
             return;
         }
         temp = temp.next;
     }
 }

 // show books from start
 void showForward() {
     BookNode temp = head;
     while (temp != null) {
         System.out.println(
             temp.bookId + " | " + temp.title +
             " | " + temp.author +
             " | " + temp.genre +
             " | Available- " + temp.available
         );
         temp = temp.next;
     }
 }

 // show books from last
 void showReverse() {
     BookNode temp = tail;
     while (temp != null) {
         System.out.println(
             temp.bookId + " | " + temp.title +
             " | " + temp.author +
             " | " + temp.genre +
             " | Available- " + temp.available
         );
         temp = temp.prev;
     }
 }

 // count number of books
 int countBooks() {
     int c = 0;
     BookNode temp = head;
     while (temp != null) {
         c++;
         temp = temp.next;
     }
     return c;
 }
}

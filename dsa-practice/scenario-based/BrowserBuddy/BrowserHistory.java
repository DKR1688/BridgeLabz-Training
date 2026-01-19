package BrowserBuddy;

//Node class to implementation of doubly linked list to move forward and backward
class Node {
 String data;
 Node next, prev; // reference of previous and next tab

 public Node(String data) {
     this.data = data;
     this.next = null;
     this.prev = null;
 }
}

public class BrowserHistory {
 private Node current;  // store the current node

 // search engine is mainly the homepage
 public BrowserHistory(String homepage) {
     current = new Node(homepage);
 }

 // method to visit a new site
 public void visit(String data) {
     Node newNode = new Node(data); // creating a new site node with url

     // new node becomes current
     current.next = newNode;
     newNode.prev = current;
     current = newNode;
 }

 // method to move backward
 public String backwards() {
     if (current.prev != null) { // if previous of current is not null
         current = current.prev;
     }
     return current.data;
 }

 // method to move forward
 public String forward() {
     if (current.next != null) { // check if there is next tab or not
         current = current.next;  // assign the current to next tab
     }
     return current.data;
 }

 // method to get the current data or url
 public String getCurrent() {
     return current.data;
 }
}
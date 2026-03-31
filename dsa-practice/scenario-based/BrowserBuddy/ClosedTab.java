package BrowserBuddy;

//Node-like class for stack implementation
class StackNode {
 String data;      // store data
 StackNode below;  // reference to the node below

 public StackNode(String data) {
     this.data = data;
     this.below = null;
 }
}

public class ClosedTab {
 private StackNode top; // top of the stack

 // method to push the element to the stack
 public void push(String data) {
     StackNode newNode = new StackNode(data);

     newNode.below = top; // top is under the new node
     top = newNode;       // now new node is top
 }

 // method to return and remove the top element
 public String pop() {
     if (top == null) { // check if top is null or not
         System.out.println("No closed tab to restore");
         return null;
     }
     String url = top.data; // storing the top data to url

     top = top.below; // the below tab is now on the top
     return url;
 }
}

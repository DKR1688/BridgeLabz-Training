package LinkedList;
// Singly Linked List : Inventory Managment System

public class InventoryManagementSystem {
	public static void main(String[] args) {
        Inventory inv = new Inventory();

        System.out.println("Adding items in inventory...\n");

        inv.addFirst("Laptop", "I001", 10, 55000);
        inv.addLast("Mouse", "I002", 50, 450);
        inv.addLast("Keyboard", "I003", 30, 1200);
        inv.addAt(1, "Monitor", "I004", 15, 9000);

        System.out.println("Initial Inventory List is- ");
        inv.display();

        inv.updateQuantity("I002", 60);
        System.out.println("\nAfter updating quntity of Mouse item- ");
        inv.display();

        inv.removeById("I004");
        System.out.println("\nAfter removing Monitor from inventory- ");
        inv.display();

        System.out.println("\nSearching item using ID I003...");
        System.out.println("Item found is- " + inv.searchById("I003").itemName);

        System.out.println("\nSearching item using Name Laptop...");
        System.out.println("Item ID is- " + inv.searchByName("Laptop").itemId);

        System.out.println("\nTotal value of inventory is- " + inv.totalValue());

        inv.sortByName();
        System.out.println("\nInventory after sorting by Name- ");
        inv.display();

        inv.sortByPrice();
        System.out.println("\nInventory after sorting by Price- ");
        inv.display();
    }
}

// InventoryNode class store item data
class InventoryNode {
    String itemName;
    String itemId;
    int quantity;
    double price;
    InventoryNode next; // store address of next node

    // constructor for inventory node
    InventoryNode(String itemName, String itemId, int quantity, double price) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.next = null;
    }
}

// Inventory class handle all linked list operations
class Inventory {
    InventoryNode head; // starting node of linked list

    // add item at starting position
    public void addFirst(String name, String id, int qty, double price) {
        InventoryNode newNode = new InventoryNode(name, id, qty, price);
        newNode.next = head;
        head = newNode;
    }

    // add item at last of list
    public void addLast(String name, String id, int qty, double price) {
        InventoryNode newNode = new InventoryNode(name, id, qty, price);
        if (head == null) {
            head = newNode;
            return;
        }
        InventoryNode temp = head;
        while (temp.next != null)
            temp = temp.next;
        temp.next = newNode;
    }

    // add item at specific postion
    public void addAt(int pos, String name, String id, int qty, double price) {
        if (pos == 0) {
            addFirst(name, id, qty, price);
            return;
        }
        InventoryNode newNode = new InventoryNode(name, id, qty, price);
        InventoryNode temp = head;

        for (int i = 0; i < pos - 1 && temp != null; i++) {
            temp = temp.next;
        }

        if (temp == null) return; // if postion is invalid
        newNode.next = temp.next;
        temp.next = newNode;
    }

    // remove item using item id
    public void removeById(String id) {
        if (head == null) return;

        if (head.itemId.equals(id)) {
            head = head.next;
            return;
        }

        InventoryNode temp = head;
        while (temp.next != null && !temp.next.itemId.equals(id)) {
            temp = temp.next;
        }

        if (temp.next != null)
            temp.next = temp.next.next;
    }

    // update quantity of item using id
    public void updateQuantity(String id, int newQty) {
        InventoryNode temp = head;
        while (temp != null) {
            if (temp.itemId.equals(id)) {
                temp.quantity = newQty;
                return;
            }
            temp = temp.next;
        }
    }

    // search item using id
    public InventoryNode searchById(String id) {
        InventoryNode temp = head;
        while (temp != null) {
            if (temp.itemId.equals(id))
                return temp;
            temp = temp.next;
        }
        return null;
    }

    // search item using name
    public InventoryNode searchByName(String name) {
        InventoryNode temp = head;
        while (temp != null) {
            if (temp.itemName.equalsIgnoreCase(name))
                return temp;
            temp = temp.next;
        }
        return null;
    }

    // calculate total inventory value
    public double totalValue() {
        double sum = 0;
        InventoryNode temp = head;
        while (temp != null) {
            sum = sum + (temp.price * temp.quantity);
            temp = temp.next;
        }
        return sum;
    }

    // sort items by name using bubble sort
    public void sortByName() {
        for (InventoryNode i = head; i != null; i = i.next) {
            for (InventoryNode j = i.next; j != null; j = j.next) {
                if (i.itemName.compareToIgnoreCase(j.itemName) > 0) {
                    swap(i, j);
                }
            }
        }
    }

    // sort items by price
    public void sortByPrice() {
        for (InventoryNode i = head; i != null; i = i.next) {
            for (InventoryNode j = i.next; j != null; j = j.next) {
                if (i.price > j.price) {
                    swap(i, j);
                }
            }
        }
    }

    // swap data between two inventory nodes
    private void swap(InventoryNode a, InventoryNode b) {
        String tmpName = a.itemName; a.itemName = b.itemName; b.itemName = tmpName;
        String tmpId = a.itemId; a.itemId = b.itemId; b.itemId = tmpId;
        int tmpQty = a.quantity; a.quantity = b.quantity; b.quantity = tmpQty;
        double tmpPrice = a.price; a.price = b.price; b.price = tmpPrice;
    }

    // display all inventory items
    public void display() {
        InventoryNode temp = head;
        while (temp != null) {
            System.out.println(
                temp.itemId + " | " + temp.itemName +
                " | Qty- " + temp.quantity +
                " | Price- " + temp.price
            );
            temp = temp.next;
        }
    }
}

package BrowserBuddy;

import java.util.Scanner;
public class Browser {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // creating object of history with search engine google
        BrowserHistory history = new BrowserHistory("google.com");

        // creating an object of closed tab
        ClosedTab tab = new ClosedTab();

        while (true) { // loop runs until user chooses exit
            // provide choices to user
            System.out.println("\n--- BrowserBuddy ---");
            System.out.println("Current Tab: " + history.getCurrent());
            System.out.println("Press 1. Visit New site");
            System.out.println("Press 2. Back");
            System.out.println("Press 3. Forward");
            System.out.println("Press 4. Close and save Current Tab");
            System.out.println("Press 5. Restore Last Closed Tab");
            System.out.println("Press 6. Exit");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(sc.nextLine()); // taking user choice

            // run the function according to the user choice
            switch (choice) {
                case 1:
                    System.out.print("Enter Url: ");
                    history.visit(sc.nextLine()); // visit new site
                    break;
                case 2:
                    System.out.println("Moved back: " + history.backwards()); // move backward
                    break;
                case 3:
                    System.out.println("Moved forward: " + history.forward()); // move forward
                    break;
                case 4:
                    tab.push(history.getCurrent()); // push the current tab to stack
                    System.out.println("Tab closed and saved to stack");
                    history.backwards(); // close the tab by moving backward
                    break;
                case 5:
                    String restore = tab.pop(); // remove and return the top of the stack

                    if (restore != null) { // check if restore is null or not
                        history.visit(restore); // visit the restored site
                        System.out.println("Restored: " + restore);
                    } else {
                        System.out.println("No tab to restore");
                    }
                    break;
                case 6:
                    System.out.println("Exiting BrowserBuddy...");
                    return; // exit from the loop
            }
        }
    }
}

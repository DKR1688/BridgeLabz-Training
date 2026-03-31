package LinkedList;
//Doubly Linked List: Undo/Redo Functionality for Text Editor

public class UndoFunctionality{
    public static void main(String[] args) {
        TextEditorHistory editor = new TextEditorHistory();

        editor.addState("Hello");
        editor.addState("Hello World");
        editor.addState("Hello World!");
        editor.displayCurrent();

        editor.undo();
        editor.displayCurrent();

        editor.undo();
        editor.displayCurrent();

        editor.redo();
        editor.displayCurrent();

        editor.addState("Hello World!!");
        editor.displayCurrent();

        editor.redo(); // should not redo
        editor.displayCurrent();

        editor.displayHistory();
    }
}
class TextState {
    String content;
    TextState prev;
    TextState next;

    TextState(String content) {
        this.content = content;
        this.prev = null;
        this.next = null;
    }
}

class TextEditorHistory {
    private TextState head;
    private TextState tail;
    private TextState current;
    private int size;
    private final int MAX_HISTORY = 10;

    // Add new state (typing or action)
    public void addState(String text) {
        TextState newState = new TextState(text);

        // If undo was used, clear redo history
        if (current != null && current.next != null) {
            current.next.prev = null;
            current.next = null;
            tail = current;
        }

        if (head == null) {
            head = tail = current = newState;
            size = 1;
            return;
        }

        tail.next = newState;
        newState.prev = tail;
        tail = newState;
        current = newState;
        size++;

        // Maintain fixed history size
        if (size > MAX_HISTORY) {
            head = head.next;
            head.prev = null;
            size--;
        }
    }

    // Undo operation
    public void undo() {
        if (current == null || current.prev == null) {
            System.out.println("Nothing to undo.");
            return;
        }
        current = current.prev;
    }

    // Redo operation
    public void redo() {
        if (current == null || current.next == null) {
            System.out.println("Nothing to redo.");
            return;
        }
        current = current.next;
    }

    // Display current text
    public void displayCurrent() {
        if (current == null) {
            System.out.println("Editor is empty.");
            return;
        }
        System.out.println("Current Text: " + current.content);
    }

    // Display full history (optional, for understanding)
    public void displayHistory() {
        TextState temp = head;
        System.out.println("\nEditor History:");
        while (temp != null) {
            if (temp == current)
                System.out.println("-> " + temp.content);
            else
                System.out.println("   " + temp.content);
            temp = temp.next;
        }
    }
}
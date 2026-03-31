package BrowserBuddy;

public interface History {
    void visit(String data);
    String backwards();
    String forward();
    String getCurrent();
}
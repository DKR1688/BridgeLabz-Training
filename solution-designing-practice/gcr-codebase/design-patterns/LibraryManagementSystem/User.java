package LibraryManagementSystem;

public interface User extends Observer {
    void showRole();
    String getName();
}

class Student implements User {
    private String name;
    public Student(String name) { 
    	this.name = name; 
    }
    
    public void showRole() { 
    	System.out.println(name + " is a Student.");
    }
    
    public void update(String msg) { 
    	System.out.println(name + " notified- " + msg); 
    }
    
    public String getName() { 
    	return name; 
    }
}

class Faculty implements User {
    private String name;
    public Faculty(String name) { 
    	this.name =name; 
    }
    
    public void showRole() { 
    	System.out.println(name + " is a Faculty."); 
    }
    
    public void update(String msg) { 
    	System.out.println(name + " notified- " + msg); 
    }
    
    public String getName() { 
    	return name; 
    }
}

class Librarian implements User {
    private String name;
    public Librarian(String name) { 
    	this.name =name; 
    }
    public void showRole() { 
    	System.out.println(name + " is a Librarian."); 
    }
    
    public void update(String msg) { 
    	System.out.println(name + " notified- " + msg); 
    }
    
    public String getName() { 
    	return name; 
    }
}

class Guest implements User {
    private String name;
    public Guest(String name) { 
    	this.name = name; 
    }
    
    public void showRole() {
    	System.out.println(name + " is a Guest."); 
    }
    
    public void update(String msg) { 
    	System.out.println(name + " notified- " +msg); 
    }
    
    public String getName() { 
    	return name; 
    }
}

class UserFactory {
    public static User createUser(String type, String name) {
        switch (type.toLowerCase()) {
            case "student": 
            	return new Student(name);
            case "faculty": 
            	return new Faculty(name);
            case "librarian": 
            	return new Librarian(name);
            case "guest": 
            	return new Guest(name);
            default: 
            	throw new IllegalArgumentException("Unknown type");
        }
    }
}
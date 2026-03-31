package LinkedList;
//Singly Linked List: Student Record Management

public class StudentRecordManagement {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        SinglyLinkedList list = new SinglyLinkedList();

        //adding students
        list.addStudentAtBeginning("Deepak", 101, 20, 'A');
        list.addStudentAtEnd("Abhay", 102, 21, 'B');
        list.addStudentAtPosition(2, "Rajput", 103, 22, 'C');
        System.out.println();

        //displaying students
        list.displayRecords();
        System.out.println();

        //searching students
        list.searchByRollNo(102);
        System.out.println();

        //updating grade
        list.updateGrade(103, 'A');
        System.out.println();

        //deleting student
        list.deleteStudentByRollNo(101);
        System.out.println();
        
        //display after deletion
        list.displayRecords();
        System.out.println();
	}
}

//this is node class to point each student
class NodeStudent{
	String name;
	int rollNumber;
	int age;
	char grade;
	NodeStudent next;
	
	public NodeStudent(String name, int rollNumber, int age, char grade){
		this.name =name;
		this.age =age;
		this.rollNumber =rollNumber;
		this.grade =grade;
		this.next =null;
	}
}

class SinglyLinkedList{
	NodeStudent headStudent;
	
	void addStudentAtBeginning(String name, int rollNumber, int age, char grade) {
		NodeStudent newStudent =new NodeStudent(name, rollNumber, age, grade);
		if(headStudent==null) {
			headStudent=newStudent;
			return;
		}
	}
	
	void addStudentAtEnd(String name, int rollNumber, int age, char grade) {
		NodeStudent newStudent =new NodeStudent(name, rollNumber, age, grade);
		if(headStudent==null) {
			headStudent=newStudent;
			return;
		}
		
		NodeStudent tempStudent =headStudent;
		while(tempStudent != null) {
			tempStudent=tempStudent.next;
		}
		tempStudent =newStudent;
	}
	
	void addStudentAtPosition(int position, String name, int rollNumber, int age, char grade) {
		NodeStudent newStudent =new NodeStudent(name, rollNumber, age, grade);
		if(position<=0) {
			System.out.println("Invalid position to add newNode");
			return;					
		}
		if(position==1) {
			headStudent =newStudent;
			return;
		}
		
		NodeStudent tempStudent =headStudent;
		for(int i=1; i<position-1; i++) {
			tempStudent =tempStudent.next;
		}
		newStudent.next =tempStudent.next;
		tempStudent.next =newStudent;
	}
	
	void deleteStudentByRollNo(int rollNo) {
		if(headStudent==null) {
			System.out.println("List is empty");
			return;
		}
		if(headStudent.rollNumber==rollNo) {
			headStudent =headStudent.next;
			System.out.println("Student rollNo "+rollNo+ "deleted successfully.");
			return;
		}
		NodeStudent tempStudent =headStudent;
		while(tempStudent.next.rollNumber!=rollNo) {
			tempStudent =tempStudent.next;
		}
		if(tempStudent.next==null) {
			System.out.println("RollNo not found.");
		}else {
			System.out.println("Student rollNo "+rollNo+ "deleted successfully.");
		}
	}
	
	void searchByRollNo(int rollNo) {
        NodeStudent tempStudent = headStudent;
        while (tempStudent!=null) {
            if (tempStudent.rollNumber == rollNo) {
                System.out.println("Student Found- RollNo- " + tempStudent.rollNumber + "\n Name=" + tempStudent.name +
                        "\n Age=" + tempStudent.age + "\n Grade- " + tempStudent.grade);
                return;
            }
            tempStudent= tempStudent.next;
        }
        System.out.println("Student not found.");
    }
	
	void updateGrade(int rollNo, char newGrade) {
        NodeStudent tempStudent = headStudent;
        while (tempStudent!= null) {
            if (tempStudent.rollNumber == rollNo) {
                tempStudent.grade = newGrade;
                System.out.println("Student grade updated successfully.");
                return;
            }
            tempStudent= tempStudent.next;
        }
        System.out.println("Student not found.");
    }
	
	void displayRecords() {
        if (headStudent== null) {
            System.out.println("No students to display.");
            return;
        }
        NodeStudent tempStudent= headStudent;
        System.out.println("Student Records- ");
        while (tempStudent != null) {
            System.out.println("RollNo- " + tempStudent.rollNumber + "\n Name- " + tempStudent.name +
                    "\n Age- " + tempStudent.age + "\n Grade- " + tempStudent.grade);
            tempStudent = tempStudent.next;
        }
    }
}

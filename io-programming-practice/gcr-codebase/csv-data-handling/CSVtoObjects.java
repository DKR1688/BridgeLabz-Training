import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class Student {
    int id;
    String name;
    int age;

    Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + age;
    }
}

public class CSVtoObjects {

    public static void main(String[] args) throws Exception {

        List<Student> students = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader("D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\student.csv"));

        br.readLine();
        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

            Student s = new Student( Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]));
            students.add(s);
        }

        br.close();

        students.forEach(System.out::println);
    }
}

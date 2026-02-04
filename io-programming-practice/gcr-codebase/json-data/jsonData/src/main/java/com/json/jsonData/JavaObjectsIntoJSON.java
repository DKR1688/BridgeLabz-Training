package com.json.jsonData;

import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
class Student2 {
    String name;
    int age;

    Student2(String name, int age) {
        this.name =name;
        this.age =age;
    }

    public String getName() {
    	return name; 
    }
    public int getAge() { 
    	return age; 
    }
}

public class JavaObjectsIntoJSON {
	public static void main(String[] args) throws Exception {
        List<Student2> students = new ArrayList<>();
        students.add(new Student2("Deepak", 23));
        students.add(new Student2("Abhay", 24));

        ObjectMapper mapper=new ObjectMapper();

        //converting list to JSON array string
        String jsonArray =mapper.writeValueAsString(students);
        System.out.println(jsonArray);
    }
}
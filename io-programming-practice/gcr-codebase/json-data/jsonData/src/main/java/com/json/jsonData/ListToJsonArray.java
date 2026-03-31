package com.json.jsonData;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

class Student4 {
    public String name;
    public int age;

    Student4(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class ListToJsonArray {

	public static void main(String[] args) {
		try {
            List<Student4> students = new ArrayList<>();
            students.add(new Student4("Tanuj", 21));
            students.add(new Student4("Amit", 23));
            students.add(new Student4("Neha", 22));

            ObjectMapper mapper = new ObjectMapper();
            String jsonArray = mapper.writeValueAsString(students);
            System.out.println(jsonArray);

        } catch (Exception e) {
            e.printStackTrace();
        }

	}

}
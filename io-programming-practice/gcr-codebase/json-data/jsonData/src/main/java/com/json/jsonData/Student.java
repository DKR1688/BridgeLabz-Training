package com.json.jsonData;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;
class Student {
    String name;
    int age;
    List<String> subjects;

    Student(String name, int age, List<String> subjects) {
        this.name =name;
        this.age =age;
        this.subjects =subjects;
    }

    public static void main(String[] args) {
        Student student=new Student("Deepak Kumar Rajput", 22,
            Arrays.asList("Computer Science", "Mathematics", "Data Structures", "Operating Systems")); 

        Gson gson =new Gson();
        String json =gson.toJson(student);

        System.out.println("Generated JSON is- ");
        System.out.println(json);
    }
}


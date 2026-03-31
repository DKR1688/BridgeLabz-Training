package com.json.jsonData;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
class Student3 {
    String name;
    int age;
    
    //default constructor needed for jackson.
    Student3() {}

    Student3(String name, int age) {
        this.name=name;
        this.age =age;
    }
    
    public String getName() { 
    	return name; 
    }
    public int getAge() { 
    	return age;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + "}";
    }
}

public class ParsingAndFiltering {
	public static void main(String[] args) throws Exception {
        ObjectMapper mapper=new ObjectMapper();
        String filePath= "D:\\BridgeLabz-Training\\io-programming-practice\\gcr-codebase\\json-data\\jsonData\\src\\main\\java\\com\\json\\jsonData\\student3.json";
        
        List<Student3> students =mapper.readValue(new File(filePath),
        							new TypeReference<List<Student3>>() {});

        //filtering only those with age>25
        List<Student3> filtered = students.stream().filter(s -> s.getAge() > 25)
                                         .collect(Collectors.toList());

        System.out.println("Filtered students are- "+filtered);
    }
}

package com.json.jsonData;

import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;

class User{
	public String name;
	public String email;
	public int age;
	public String city;
	
}

public class ReadJSON {

	public static void main(String[] args) {
		try {
			ObjectMapper obj = new ObjectMapper();
			User user = obj.readValue(new File("D:\\BridgeLabz-Training\\io-programming-practice\\gcr-codebase\\json-data\\jsonData\\src\\main\\java\\com\\json\\jsonData\\student.json"),
					User.class);
			System.out.println("User name- "+user.name);
			System.out.println("User email- "+user.email);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
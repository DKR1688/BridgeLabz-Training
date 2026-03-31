package com.json.jsonData;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReader {
    public static void main(String[] args) {
        try {
            //reading JSON file into a String
            String data =new String(Files.readAllBytes(Paths.get("D:\\BridgeLabz-Training\\io-programming-practice\\gcr-codebase\\json-data\\jsonData\\src\\main\\java\\com\\json\\jsonData\\student.json")));

            JSONObject jsonObject = new JSONObject(data);
            String name = jsonObject.getString("name");
            String email = jsonObject.getString("email");

            System.out.println("Name- " +name);
            System.out.println("Email- " +email);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
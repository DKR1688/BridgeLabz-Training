package com.json.jsonData;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
public class ValidateJSON {
	public static void main(String[] args) {
        String filePath="D:\\BridgeLabz-Training\\io-programming-practice\\gcr-codebase\\json-data\\jsonData\\src\\main\\java\\com\\json\\jsonData\\student.json";

        ObjectMapper mapper =new ObjectMapper();
        System.out.println("Valid JSON? "+isValidJsonFile(mapper, filePath));
    }

    public static boolean isValidJsonFile(ObjectMapper mapper, String filePath) {
        try {
            mapper.readTree(new File(filePath));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}

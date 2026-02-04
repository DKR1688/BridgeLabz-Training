package com.json.jsonData;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
public class ValidateEmail {
	public static void main(String[] args) throws Exception{
		String json = """
                {"email": "deepak@gmail.com"}
                """;
		ObjectMapper obj =new ObjectMapper();
		JsonNode node =obj.readTree(json);
		
		if (!node.has("email")) {
            System.out.println("Email field missing");
            return;
        }
		
		String email = node.get("email").asText();
		boolean isValid = email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        if (isValid) {
            System.out.println("Valid email");
        } else {
            System.out.println("Invalid email");
        }
	}
}
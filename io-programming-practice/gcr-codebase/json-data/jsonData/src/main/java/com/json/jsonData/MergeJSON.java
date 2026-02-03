package com.json.jsonData;

import org.json.JSONObject;

public class MergeJSON {
	public static void main(String[] args) {
        JSONObject json1 =new JSONObject();
        json1.put("name", "Deepak Kumar Rajput");
        json1.put("email", "deepak@example.com");

        JSONObject json2 =new JSONObject();
        json2.put("age", 22);
        json2.put("city", "Mathura");

        for (String key: json2.keySet()) {
            json1.put(key, json2.get(key));
        }

        System.out.println("Merged JSON object are- ");
        System.out.println(json1.toString());
    }
}

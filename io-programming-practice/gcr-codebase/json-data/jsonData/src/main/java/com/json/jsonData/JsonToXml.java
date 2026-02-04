package com.json.jsonData;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
public class JsonToXml {
	public static void main(String[] args) throws Exception{
		String json = "{ \"name\": \"harshdeep\", \"age\": 22, \"city\": \"Mathura\" }";
		ObjectMapper obj =new ObjectMapper();
		JsonNode root =obj.readTree(json);
		
		XmlMapper xmlMapper=new XmlMapper();
		 
        String xml =xmlMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(root);

        System.out.println(xml);
	}
}
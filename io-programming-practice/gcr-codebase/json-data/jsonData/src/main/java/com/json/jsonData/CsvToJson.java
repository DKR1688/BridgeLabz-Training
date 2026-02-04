package com.json.jsonData;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CsvToJson {
    public static void main(String[] args) throws Exception {
        File csvFile = new File("D:\\BridgeLabz-Training\\io-programming-practice\\gcr-codebase\\json-data\\jsonData\\src\\main\\java\\com\\json\\jsonData\\student.json");

        //creating CSV mapper and schema
        CsvSchema schema =CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper =new CsvMapper();

        //Use of mapping iterator to read all rows into a list
        MappingIterator<Map<String, String>> it = csvMapper
                .readerFor(Map.class)
                .with(schema)
                .readValues(csvFile);

        List<Map<String, String>> rows = it.readAll();

        //converting list to JSON
        ObjectMapper jsonMapper =new ObjectMapper();
        String jsonArray =jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rows);

        System.out.println("CSV → JSON:");
        System.out.println(jsonArray);
    }
}
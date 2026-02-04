package com.json.jsonData;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

public class IPLCensorAnalyzer {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        //JSON Processing
        File jsonFile = new File("D:\\BridgeLabz-Training\\io-programming-practice\\gcr-codebase\\json-data\\jsonData\\src\\main\\java\\com\\json\\jsonData\\matches.json"); // relative path
        List<Map<String, Object>> matches =
                mapper.readValue(jsonFile, List.class);

        List<Map<String, Object>> censoredJson = new ArrayList<>();
        for (Map<String, Object> match : matches) {
            censoredJson.add(censorMatch(match));
        }

        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File("matches_censored.json"), censoredJson);

        //CSV Processing
        processCSV("D:\\BridgeLabz-Training\\io-programming-practice\\gcr-codebase\\json-data\\jsonData\\src\\main\\java\\com\\json\\jsonData\\matches.csv", "matches_censored.csv");

        System.out.println("Censorship completed for JSON and CSV.");
    }

    //centralized censorship logic
    static Map<String, Object> censorMatch(Map<String, Object> match) {
        match.put("team1", maskTeam((String) match.get("team1")));
        match.put("team2", maskTeam((String) match.get("team2")));
        match.put("playerOfMatch", "REDACTED");
        return match;
    }

    static String maskTeam(String team) {
        if (team == null) return null;
        String[] parts = team.split(" ");
        return parts.length > 1 ? parts[0] + " ***" : team + " ***";
    }

    static void processCSV(String inputPath, String outputPath) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {

            //write header unchanged
            bw.write(br.readLine());
            bw.newLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                d[1] = maskTeam(d[1]);  
                d[2] = maskTeam(d[2]);  
                d[3] = "REDACTED";     

                bw.write(String.join(",", d));
                bw.newLine();
            }
        }
    }
}
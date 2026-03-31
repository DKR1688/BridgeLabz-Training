package M1_Set4;

import java.util.*;
import java.util.regex.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DynamicTempateProcessor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.nextLine());

        Pattern pattern = Pattern.compile("\\$\\{([A-Z]+):([^}]*)\\}");

        for (int i = 0; i < N; i++) {
            String line = sc.nextLine();
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                String type = matcher.group(1);
                String value = matcher.group(2);  
                String replacement = process(type, value);
                line = line.replace(matcher.group(0), replacement);
            }

            System.out.println(line);
        }
    }

    private static String process(String type, String value) {
        switch (type) {
            case "DATE":
                try {
                    DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate date = LocalDate.parse(value, inputFmt);
                    DateTimeFormatter outputFmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    return date.format(outputFmt);
                } catch (DateTimeParseException e) {
                    return "INVALID";
                }
            case "UPPER":
                return value.toUpperCase();
            case "LOWER":
                return value.toLowerCase();
            case "REPEAT":
                String[] parts = value.split(",");
                if (parts.length == 2) {
                    try {
                        int count = Integer.parseInt(parts[1]);
                        return parts[0].repeat(count);
                    } catch (NumberFormatException e) {
                        return "INVALID";
                    }
                }
                return "INVALID";
            default:
                return "INVALID";
        }
    }
}
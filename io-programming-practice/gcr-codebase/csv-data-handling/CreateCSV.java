import java.io.FileWriter;
import java.io.IOException;

public class CreateCSV {

    public static void main(String[] args) {

        String filePath = "D:\\BT\\BridgeLabz-Training\\io-programming-practice\\csvDataHandling\\employee.csv";

        try (FileWriter fw = new FileWriter(filePath)) {

            // Header
            fw.write("ID,Name,Department,Salary\n");

            // Employee records
            fw.write("101,Abhay,IT,55000\n");
            fw.write("102,Rahul,HR,45000\n");
            fw.write("103,Priya,Finance,60000\n");
            fw.write("104,Amit,Sales,50000\n");
            fw.write("105,Sneha,Marketing,52000\n");

            System.out.println("Employee CSV file created successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

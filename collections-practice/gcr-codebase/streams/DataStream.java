import java.io.*;
public class DataStream {
    public static void main(String[] args) {
        String filename="students.dat";

        //writing student details into binary file
        try (DataOutputStream dos=new DataOutputStream(new FileOutputStream(filename))) {
            dos.writeInt(1);           
            dos.writeUTF("Deepak");   
            dos.writeDouble(7.6);    

            dos.writeInt(2);
            dos.writeUTF("Abay");
            dos.writeDouble(7.2);

            dos.writeInt(3);
            dos.writeUTF("Rajput");
            dos.writeDouble(9.1);

            System.out.println("Student details written to file- "+filename);
        } catch (IOException e) {
            System.out.println("Error writing file- "+e.getMessage());
        }

        //Reading student details back from binary file
        try (DataInputStream dis=new DataInputStream(new FileInputStream(filename))) {
            System.out.println("\nRetrieving student details from file- ");

            while (dis.available()>0) {
                int roll =dis.readInt();
                String name =dis.readUTF();
                double cgpa =dis.readDouble();

                System.out.println("Roll- " +roll+ "\n Name- " + name + "\n CGPA- "+cgpa);
            }
        } catch (IOException e) {
            System.out.println("Error reading file- "+e.getMessage());
        }
    }
}
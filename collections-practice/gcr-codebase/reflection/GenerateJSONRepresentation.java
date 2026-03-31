import java.lang.reflect.*;

class Employee {
    public String name = "John";
    public int id = 101;
}

public class GenerateJSONRepresentation {
    public static String toJson(Object obj) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            sb.append("\"" + fields[i].getName() + "\": ");
            Object value = fields[i].get(obj);
            if (value instanceof String) sb.append("\"" + value + "\"");
            else sb.append(value);
            if (i < fields.length - 1) sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }
    public static void main(String[] args) throws Exception {
        Employee emp = new Employee();
        System.out.println(toJson(emp));
    }
}


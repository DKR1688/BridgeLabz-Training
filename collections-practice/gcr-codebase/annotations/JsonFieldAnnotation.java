import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@interface JsonField {
    String name();
}

class JsonUser {
    @JsonField(name = "user_name")
    private String username = "alice";
    @JsonField(name = "user_age")
    private int age = 30;
}

public class JsonFieldAnnotation {
    public static String toJson(Object obj) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Field[] fields = obj.getClass().getDeclaredFields();
        boolean first = true;
        for (Field field : fields) {
            field.setAccessible(true);
            JsonField jf = field.getAnnotation(JsonField.class);
            if (jf != null) {
                if (!first) sb.append(", ");
                sb.append("\"" + jf.name() + "\": ");
                Object value = field.get(obj);
                if (value instanceof String) sb.append("\"" + value + "\"");
                else sb.append(value);
                first = false;
            }
        }
        sb.append("}");
        return sb.toString();
    }
    public static void main(String[] args) throws Exception {
        JsonUser user = new JsonUser();
        System.out.println(toJson(user));
    }
}

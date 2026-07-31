import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(BugReports.class)
@interface BugReport {
    String description();
}

@Retention(RetentionPolicy.RUNTIME)
@interface BugReports {
    BugReport[] value();
}

class Software {
    @BugReport(description = "Null pointer issue")
    @BugReport(description = "Performance lag")
    public void process() {}
}

public class RepeatableBugReportAnnotation {
    public static void main(String[] args) throws Exception {
        Method m = Software.class.getDeclaredMethod("process");
        BugReport[] reports = m.getAnnotationsByType(BugReport.class);
        for (BugReport report : reports) {
            System.out.println("Bug: " + report.description());
        }
    }
}
class Animals {
    public void makeSound() {
        System.out.println("Animal sound");
    }
}

class Dogs extends Animals {
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
}

class Q1_OverrideAnnotation {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.makeSound();
    }
}


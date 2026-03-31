import java.util.Arrays;
import java.util.List;

public class HospitalPatientID {
    public static void main(String[] args) {
        List<Integer> patientIds =Arrays.asList(101, 102, 103, 104, 105);

        //method reference instead of lambda
        patientIds.forEach(System.out::println);
    }
}

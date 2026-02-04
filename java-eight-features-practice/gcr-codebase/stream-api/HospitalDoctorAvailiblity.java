import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Doctor {
    private String name;
    private String specialty;
    private boolean weekendAvailable;

    public Doctor(String name, String specialty, boolean Availablity) {
        this.name = name;
        this.specialty = specialty;
        this.weekendAvailable = Availablity;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public boolean isWeekendAvailable() {
        return weekendAvailable;
    }

    @Override
    public String toString() {
        return name + " is specialized in " + specialty;
    }
}

public class HospitalDoctorAvailiblity {
    public static void main(String[] args) {

        List<Doctor> doctors = Arrays.asList(
            new Doctor("Abhay", "Software", true),
            new Doctor("Deepak", "Dentist", false),
            new Doctor("Abhishek", "Optic", true),
            new Doctor("Arjun", "Legs", false),
            new Doctor("Radhika", "ENT", true),
            new Doctor("Anuj", "Software", true),
            new Doctor("ABCDEF", "Nurse", false)
        );

        System.out.println("Doctors available on weekends (sorted by specialty):");

        doctors.stream()
               .filter(Doctor::isWeekendAvailable)
               .sorted(Comparator.comparing(Doctor::getSpecialty))
               .forEach(System.out::println);
    }
}

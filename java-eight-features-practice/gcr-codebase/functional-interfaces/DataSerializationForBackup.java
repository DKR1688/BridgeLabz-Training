import java.io.Serializable;
public class DataSerializationForBackup {
    public static void main(String[] args) {
        PatientRecord record = new PatientRecord(1, "Abhay");
        BackupService.backup(record);
    }
}

class PatientRecord implements Serializable {
    int id;
    String name;

    PatientRecord(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class BackupService {
    static void backup(Object obj) {
        if (obj instanceof Serializable) {
            System.out.println("Backing up object- " + obj.getClass().getSimpleName());
        } else {
            System.out.println("Object NOT eligible for backup");
        }
    }
}

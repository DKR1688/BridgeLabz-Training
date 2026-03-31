package FileBackupScheduler;

public class FileBackipApp {
	public static void main(String[] args) {
        BackupScheduler scheduler=new BackupScheduler();
        try {
            scheduler.scheduleBackup(new BackupTask("C:/Study", 5, "10:00 AM"));
            scheduler.scheduleBackup(new BackupTask("D:/Movies", 1, "11:00 AM"));
            scheduler.scheduleBackup(new BackupTask("C:/Projects", 3, "12:00 PM"));
            scheduler.scheduleBackup(new BackupTask("E:/Games", 2, "01:00 PM"));

            //here we give invalid path test
            scheduler.scheduleBackup(new BackupTask("", 4, "02:00 PM"));

        }catch (InvalidBackupPathException e) {
            System.out.println("Error- "+e.getMessage());
        }

        scheduler.executeBackups();
    }
}

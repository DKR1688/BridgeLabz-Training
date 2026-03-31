package FileBackupScheduler;

import java.util.PriorityQueue;
class BackupScheduler {
    PriorityQueue<BackupTask> taskQueue = new PriorityQueue<>();

    //adding a backup task
    public void scheduleBackup(BackupTask task) {
        taskQueue.add(task);
        System.out.println("Scheduled- "+task);
    }

    //execute tasks in priority order
    public void executeBackups() {
        System.out.println("\nExecuting backups in priority order- ");
        while (!taskQueue.isEmpty()) {
            BackupTask task = taskQueue.poll();
            System.out.println("Executing backup for- "+task.getPath() +" at " + task.getScheduledTime() +
                    " [Priority- " + task.getPriority()+"]");
        }
    }
}
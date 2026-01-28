package FileBackupScheduler;

class BackupTask implements Comparable<BackupTask> {
    String path;
    int priority;
    String scheduledTime;

    BackupTask(String path, int priority, String scheduledTime) throws InvalidBackupPathException {
        if (path==null || path.isEmpty()) {
            throw new InvalidBackupPathException("Backup path cannot be empty.");
        }
        this.path =path;
        this.priority =priority;
        this.scheduledTime =scheduledTime;
    }

    public String getPath() {
        return path;
    }

    public int getPriority() {
        return priority;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    @Override
    public int compareTo(BackupTask other) {
        //here higher priority tasks come first
        return Integer.compare(other.priority, this.priority);
    }

    @Override
    public String toString() {
        return "BackupTask{" +"path='" + path + '\'' +", priority=" + priority +
                ", scheduledTime='" + scheduledTime + '\'' +'}';
    }
}
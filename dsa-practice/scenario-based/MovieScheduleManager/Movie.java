package MovieScheduleManager;

class Movie {
    private String title;
    private String time;

    public Movie(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Movie- " + title + " | Time- " + time;
    }
}


package EventFeedbackRatingAnalyser;

public class FeedbackApp{
    public static void main(String[] args) {
        EventFeedbackAnalyzer analyzer = new EventFeedbackAnalyzer();

        try {
            analyzer.addRating(101, 5);
            analyzer.addRating(101, 4);
            analyzer.addRating(102, 3);
            analyzer.addRating(102, 2);
            analyzer.addRating(103, 6);
        } catch (InvalidRatingException e) {
            System.out.println(e.getMessage());
        }

        analyzer.showReport();
    }
}

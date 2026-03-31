package EventFeedbackRatingAnalyser;

import java.util.*;
public class EventFeedbackAnalyzer {
	private Map<Integer, List<Integer>> feedbackMap;

	public EventFeedbackAnalyzer() {
		feedbackMap = new HashMap<>();
	}

	// Add rating for an event
	public void addRating(int eventId, int rating) throws InvalidRatingException {
		if (rating < 1 || rating > 5) {
			throw new InvalidRatingException("Rating must be between 1 and 5!");
		}
		feedbackMap.putIfAbsent(eventId, new ArrayList<>());
		feedbackMap.get(eventId).add(rating);
		System.out.println("Added rating " + rating + " for event " + eventId);
	}

	// Calculate average rating for a specific event
	public double getAverageRating(int eventId) {
		List<Integer> ratings = feedbackMap.get(eventId);
		if (ratings == null || ratings.isEmpty())
			return 0.0;

		double sum = 0;
		for (int r : ratings)
			sum += r;
		return sum / ratings.size();
	}

	// Identify top-rated event
	public int getTopRatedEvent() {
		double highestAvg = 0.0;
		int topEvent = -1;

		for (Map.Entry<Integer, List<Integer>> entry : feedbackMap.entrySet()) {
			double avg = getAverageRating(entry.getKey());
			if (avg > highestAvg) {
				highestAvg = avg;
				topEvent = entry.getKey();
			}
		}
		return topEvent;
	}

	// Reporting method
	public void showReport() {
		System.out.println("Event Feedback Report:");
		for (Integer eventId : feedbackMap.keySet()) {
			System.out.println("Event " + eventId + " | Avg Rating: " + getAverageRating(eventId));
		}
		int topEvent = getTopRatedEvent();
		if (topEvent != -1) {
			System.out.println("Top Rated Event: " + topEvent + " with Avg Rating " + getAverageRating(topEvent));
		}
	}
}

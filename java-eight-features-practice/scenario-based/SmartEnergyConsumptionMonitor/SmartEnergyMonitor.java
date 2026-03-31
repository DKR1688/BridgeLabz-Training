package SmartEnergyConsumptionMonitor;

import java.util.*;

public class SmartEnergyMonitor {
	private Map<Date, List<Double>> usageData;

	public SmartEnergyMonitor() {
		usageData = new HashMap<>();
	}

	// Add a reading for a given date
	public void addReading(Date date, double reading) throws InvalidEnergyReadingException {
		if (reading < 0) {
			throw new InvalidEnergyReadingException("Energy reading cannot be negative!");
		}
		usageData.putIfAbsent(date, new ArrayList<>());
		usageData.get(date).add(reading);
		System.out.println("Added reading " + reading + " kWh for " + date);
	}

	// Calculate daily average
	public double getDailyAverage(Date date) {
		List<Double> readings = usageData.get(date);
		if (readings == null || readings.isEmpty())
			return 0.0;

		double sum = 0;
		for (double r : readings)
			sum += r;
		return sum / readings.size();
	}

	// Calculate monthly average (by month/year)
	public double getMonthlyAverage(int month, int year) {
		double sum = 0;
		int count = 0;

		Calendar cal = Calendar.getInstance();
		for (Map.Entry<Date, List<Double>> entry : usageData.entrySet()) {
			cal.setTime(entry.getKey());
			int entryMonth = cal.get(Calendar.MONTH);
			int entryYear = cal.get(Calendar.YEAR);

			if (entryMonth == month && entryYear == year) {
				for (double r : entry.getValue()) {
					sum += r;
					count++;
				}
			}
		}
		return count == 0 ? 0.0 : sum / count;
	}

	// Reporting method
	public void showReport(int month, int year) {
		System.out.println("Energy Report for " + (month + 1) + "/" + year);
		double monthlyAvg = getMonthlyAverage(month, year);
		System.out.println("Monthly Average: " + monthlyAvg + " kWh");

		Calendar cal = Calendar.getInstance();
		for (Date d : usageData.keySet()) {
			cal.setTime(d);
			if (cal.get(Calendar.MONTH) == month && cal.get(Calendar.YEAR) == year) {
				System.out.println("Date: " + d + " | Daily Avg: " + getDailyAverage(d) + " kWh");
			}
		}
	}
}
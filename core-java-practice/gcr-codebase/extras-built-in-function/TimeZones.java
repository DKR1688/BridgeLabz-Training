/*Time Zones and ZonedDateTime Write a program that displays the current
time in different time zones:
➢ GMT (Greenwich Mean Time)
➢ IST (Indian Standard Time)
➢ PST (Pacific Standard Time)
Use ZonedDateTime and ZoneId to work with different time zones.*/

import java.time.format.*;
import java.time.*;
public class TimeZones {
	public static void main(String[] args) {
		
        // we will use formatter to show  time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss  z");

        // Time in GMT (Greenwich Mean Time)
        ZonedDateTime gmtTime = ZonedDateTime.now(ZoneId.of("GMT"));
        System.out.println("Current Time in GMT is - " +gmtTime.format(timeFormatter));

        // Time in IST (Indian Standard Time)
        ZonedDateTime istTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Time in IST is - " +istTime.format(timeFormatter));

        //Time in PST (Pacific Standard Time)
        ZonedDateTime pstTime = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
        System.out.println("Current Time in PST is - " +pstTime.format(timeFormatter));
    }
}

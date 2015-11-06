package Tools;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Tools {
	
	public static String formatDateToDisplay(Calendar cal) {
		return cal.toInstant().toString();
	}
	
	public static String formatDateToDisplay(Date date) {
		return date.toInstant().toString();
	}
	
	public static String formatDateToDisplay(Instant instant) {
		return instant.toString();
	}
	
	public static String formatDateToDisplay(ZonedDateTime zdt) {
		return zdt.format(DateTimeFormatter.ISO_INSTANT);
	}
	
	public static ZonedDateTime parseInstantToZonedDateTime(Instant ins) {
		return ZonedDateTime.ofInstant(ins, ZoneId.systemDefault());
	}
	
	public static ZonedDateTime parseCalendarToZonedDateTime(Calendar cal) {
		return ZonedDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId());
	}
	
	public static ZonedDateTime parseDateToZonedDateTime(Date date) {
		return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}
}

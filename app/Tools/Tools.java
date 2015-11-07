package Tools;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Tools {
	//Affichage Date
	
	public static String formatDateToDisplay(Calendar cal) {
		return cal.toInstant().toString();
	}
	
	public static String formatDateToDisplay(Date date) {
		return date.toInstant().toString();
	}
	
	public static String formatDateToDisplay(LocalDateTime ldt) {
		return ldt.format(DateTimeFormatter.ISO_INSTANT);
	}
	
	public static String formatDateToDisplay(LocalDate ld) {
		return ld.format(DateTimeFormatter.ISO_INSTANT);
	}
	
	public static String formatDateToDisplay(Instant instant) {
		return instant.toString();
	}
	
	public static String formatDateToDisplay(ZonedDateTime zdt) {
		return zdt.format(DateTimeFormatter.ISO_INSTANT);
	}
	
	//LocalDate
	
	public static LocalDate parseInstantToLocalDate(Instant ins) {
		return LocalDateTime.ofInstant(ins, ZoneId.systemDefault()).toLocalDate();
	}
	
	public static LocalDate parseCalendarToLocalDate(Calendar cal) {
		return LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate();
	}
	
	public static LocalDate parseDateToLocalDate(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
	}
	
	//LocalDateTime
	
	public static LocalDateTime parseInstantToLocalDateTime(Instant ins) {
		return LocalDateTime.ofInstant(ins, ZoneId.systemDefault());
	}
	
	public static LocalDateTime parseCalendarToLocalDateTime(Calendar cal) {
		return LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId());
	}
	
	public static LocalDateTime parseDateToLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}
	
	//ZonedDateTime
	
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

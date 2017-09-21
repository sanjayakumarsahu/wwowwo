package com.geaviation.eds.service.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateFormatUtil {
	public static final String DATE_TIME_FORMAT_STRING = "DateTime";
	
	private DateFormatUtil(){
		
	}
	
	
	public static String formatDateTime(Date date) {
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateTimeFormat.format(date);
	}
	
	public static Date parseDateTime(String date) throws ParseException {
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateTimeFormat.parse(date);
	}
	
	public static Calendar dateToCalendar(String manfactureDate)
			throws ParseException {
			SimpleDateFormat sdf= new SimpleDateFormat("yyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			Date date = sdf.parse(manfactureDate);
			calendar.setTime(date);
			return calendar;
	}

	public static Date getFormattedDate(String inputDate) throws ParseException {
		// Validating if the supplied parameters is null
		if (inputDate == null) {
			return null;
		}
		// Create SimpleDateFormat object with source string date format
		SimpleDateFormat sourceDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		// Parse the string into Date object
		Date dateObj = sourceDateFormat.parse(inputDate);
		// Create SimpleDateFormat object with desired date format
		SimpleDateFormat desiredSimpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		// Parse the date into another format
		String desiredDateFormat=desiredSimpleDateFormat.format(dateObj).toString();		
		return desiredDateFormat != null ? desiredSimpleDateFormat.parse(desiredDateFormat) : null;
	}
	
	public static Date getSVFormattedDate(String inputDate) throws ParseException {
		// Validating if the supplied parameters is null
		if (inputDate == null) {
			return null;
		}
		// Create SimpleDateFormat object with source string date format
		SimpleDateFormat sourceDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		// Parse the string into Date object
		Date dateObj = sourceDateFormat.parse(inputDate);
		// Create SimpleDateFormat object with desired date format
		SimpleDateFormat desiredSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Parse the date into another format
		String desiredDateFormat=desiredSimpleDateFormat.format(dateObj).toString();		
		return desiredDateFormat != null ? desiredSimpleDateFormat.parse(desiredDateFormat) : null;
	}

}

package com.geaviation.eds.service.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class will be used to set the 
 * date format for date fields in returned 
 * JSON object.
 */
public class DateObjectMapper extends ObjectMapper {

	public DateObjectMapper() {
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		setDateFormat(df);
	}

}

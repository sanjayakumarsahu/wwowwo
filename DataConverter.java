package com.geaviation.eds.service.common.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataConverter {

	private static final Log LOGGER = LogFactory.getLog(DataConverter.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

	public DataConverter() {
		super();
	}

	public static List<String> converStringToList(final String strtemp){

		List<String> tempData = new ArrayList<String>();
		if(DataServiceUtils.isNotNullandEmpty(strtemp)){  	

			String[] arrTemp = strtemp.split(",");
			for(int i=0;i<arrTemp.length;i++){
				tempData.add(arrTemp[i].trim());
			}
		}
		return tempData;
	}

	public BigDecimal converStringToBigdecimal(final String strtemp){

		return new BigDecimal(strtemp.replaceAll(",", ""));

	}

	public Calendar convetStringToCalendar(final String strtemp){

		Calendar calendar = Calendar.getInstance();
		try {

			Date date = sdf.parse(strtemp);
			calendar.setTime(date);
		}
		catch (ParseException exception) {
			LOGGER.error("Error in converting String to Calendar in DataConverter "+exception);
		}
		return calendar;
	}
}

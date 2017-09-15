package com.geaviation.eds.service.events.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.exception.DataServiceException;
import com.geaviation.eds.service.common.util.DataServiceUtils;
import com.geaviation.eds.service.events.app.constants.EventsAppConstants;

public class EventServiceValidator {


	private static final Log LOGGER = LogFactory.getLog(EventServiceValidator.class);
	public static final String DATA_ELEMENT_ESN = "ESN";
	public static final String ESN_REGEX_PATTERN = "\\p{Alnum}{5,10}";
	public static final int ESN_MIN_LENGTH = 5;
	public static final int ESN_MAX_LENGTH = 10;
	public static final String ERR_M_INVALID_ESN = "Invalid Engine Serial Number. Engine Serial Number should be alphanumeric having 5 to 10 characters";
	private static final String[] ERROR_UMSG_INVALID_SINGLE_ESN = {"This service accepts only single ESN number","Please provide a valid single ESN number"};
	private static final String[] ERROR_UMSG_INVALID_ESN = {"Invalid Engine Serial Number"," Engine Serial Number should be alphanumeric having 5 to 10 characters"};
	private static final String[] ERROR_ACTION_INVALID = {"Action should not be empty","Default Action - UNKNOWN"};

	private EventServiceValidator() {

	}

	public static void validateESNList(final List<String> esnList) throws DataServiceException {
		if (null == esnList || esnList.isEmpty()) {
			LOGGER.error("Exception occured in validateESNList-"+EventsAppConstants.ESN_NUMBER_MISSING);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.MISSING_INPUT_ERROR_E1001,DataServiceConstants.ERROR_STATUS,DataServiceConstants.MISSING_INPUT_MSG,EventsAppConstants.ESN_NUMBER_MISSING);
		}

		int esnListSize = esnList.size();
		if (esnListSize > 0) {
			for (String value : esnList) {
				validate(DATA_ELEMENT_ESN, value);

			}
		}
	}

	public static void validate(String type, String value) throws DataServiceException {
		if(DataServiceUtils.isNullandEmpty(value)) {
			LOGGER.error("Exception occured in validate-"+EventsAppConstants.ESN_NUMBER_MISSING);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.MISSING_INPUT_ERROR_E1001,DataServiceConstants.ERROR_STATUS,DataServiceConstants.MISSING_INPUT_MSG,EventsAppConstants.ESN_NUMBER_MISSING);
		}
		else if (DATA_ELEMENT_ESN.equalsIgnoreCase(type) && isValidValue(value) ) {

			LOGGER.error("ExceptionOccured in validate:"+EventsAppConstants.ERROR_MSG_ESN_VALIDATION_FAILED);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.MISSING_INPUT_ERROR_E1001,DataServiceConstants.ERROR_STATUS,EventsAppConstants.ERROR_MSG_ESN_VALIDATION_FAILED,value+EventsAppConstants.ERROR_SINGLE_UMSG_INVALID_ESN,ERROR_UMSG_INVALID_ESN);
		}
	}

	public static boolean isValidValue(String value) {
		boolean isValid=false;
		if (value.length() < ESN_MIN_LENGTH || value.length() > ESN_MAX_LENGTH || !value.matches(ESN_REGEX_PATTERN)) 
		{
			isValid= true;
		} 
		return isValid;
	}

	public static void validateAction(String action) throws DataServiceException{

		if(action==null || "".equalsIgnoreCase(action)){
			LOGGER.error("Exception occured in validateAction-"+DataServiceConstants.MISSING_INPUT_MSG);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.MISSING_INPUT_ERROR_E1001,DataServiceConstants.ERROR_STATUS,DataServiceConstants.MISSING_INPUT_MSG,null,ERROR_ACTION_INVALID);
		}
	}

	public static void validateSingleESN(final List<String> esnList) throws DataServiceException {

		if (null == esnList || esnList.isEmpty()) {
			LOGGER.error("Exception occured in validateSingleESN-"+EventsAppConstants.ESN_NUMBER_MISSING);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.MISSING_INPUT_ERROR_E1001,DataServiceConstants.ERROR_STATUS,DataServiceConstants.MISSING_INPUT_MSG,EventsAppConstants.ESN_NUMBER_MISSING);

		}

		if(esnList.size()>1){
			LOGGER.error("Exception occured in validateSingleESN-"+EventsAppConstants.ERROR_MSG_ESN_VALIDATION_FAILED);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.DATA_VALIDATION_ERROR_E1002,DataServiceConstants.ERROR_STATUS,EventsAppConstants.ERROR_MSG_ESN_VALIDATION_FAILED,null,ERROR_UMSG_INVALID_SINGLE_ESN);

		}

		int esnListSize = esnList.size();
		if (esnListSize > 0) {
			for (String value : esnList) {
				validate(DATA_ELEMENT_ESN, value);
			}
		}


	}
	
	public static String[] validateEventDate(String fromDate, String endDate) throws DataServiceException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.DAY_OF_MONTH, 1);
		Date dateFrom;
		Date dateEnd;
		String[] dates = new String[2];
		try {
			dateFrom = dateFormat.parse(fromDate);
			dateEnd = dateFormat.parse(endDate);
			Date cal = currentDate.getTime();
			long fromTime = dateFrom.getTime();
			long toTime = dateEnd.getTime();
			long currTime = cal.getTime();
			long diffTime;
			
			diffTime=(toTime-fromTime)/(1000*60*60*24);
			
			
			if(fromTime > currTime || toTime > currTime){
				LOGGER.error(EventsAppConstants.EVENTS_FUTUREDATE_SELECTION);
				throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR, DataServiceConstants.DATA_VALIDATION_ERROR_E1002, DataServiceConstants.ERROR_STATUS,
						EventsAppConstants.EVENTS_FUTUREDATE_SELECTION, EventsAppConstants.EVENTS_FUTUREDATE_SELECTION);
					} else if(fromTime>toTime ){
						LOGGER.error(EventsAppConstants.EVENTS_INVALID_ENDDATE);
						throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR, DataServiceConstants.DATA_VALIDATION_ERROR_E1002, DataServiceConstants.ERROR_STATUS,
								EventsAppConstants.EVENTS_INVALID_ENDDATE, EventsAppConstants.EVENTS_INVALID_ENDDATE);
					}else if(diffTime>365){
						LOGGER.error(EventsAppConstants.EVENTS_ENDDATE_SELECTION);
						throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR, DataServiceConstants.DATA_VALIDATION_ERROR_E1002, DataServiceConstants.ERROR_STATUS,
								EventsAppConstants.EVENTS_ENDDATE_SELECTION,
								EventsAppConstants.EVENTS_ENDDATE_SELECTION);
					}
				
		} catch (ParseException e) {

			LOGGER.error("Date Format Exception occured in checkDatefieldsV1 =>" + e);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR, DataServiceConstants.DATE_FORMAT_ERROR_E1008, DataServiceConstants.ERROR_STATUS,
					EventsAppConstants.EVENTS_WRONG_DATE_FORMAT, EventsAppConstants.EVENTS_WRONG_DATE_FORMAT);
		}
		return dates;
	}
	 
	
	public static void validateEventDates(String eventFromDate, String eventToDate) throws DataServiceException {
		Date eventFromDt = null;
		Date eventToDt = null;
		long daysDiff= 0L;
		eventFromDt = validateInputEventDate(eventFromDate);
		eventToDt = validateInputEventDate(eventToDate);
		if (eventFromDt!=null && eventToDate !=null){
			daysDiff = EventServiceValidator.calculateDateDiff(eventFromDt ,eventToDt);
			if(daysDiff < 0 || daysDiff > 365){
				LOGGER.error(DataServiceConstants.DATE_FORMAT_ERROR_E1008+ ":"+ EventsAppConstants.EVENTS_DATE_VALIDATION);
				throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
						DataServiceConstants.DATE_FORMAT_ERROR_E1008,DataServiceConstants.ERROR_STATUS,
						EventsAppConstants.EVENTS_DATE_VALIDATION,
						EventsAppConstants.UMSG_EVENTS_DATE_VALIDATION);
			}
		}
		
		}

	
	public static Date validateInputEventDate(String strDate)throws DataServiceException{

		Date eventEndDate = null;
		if(DataServiceUtils.isNotNullandEmpty(strDate)){
			eventEndDate = parseDateFormat(strDate);
		}
		return eventEndDate;
	}

	public static Date parseDateFormat(String fromDate) throws DataServiceException{

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try{
			sdf.setLenient(false);
			return sdf.parse(fromDate);

		}
		catch(ParseException exp)
		{
			LOGGER.error("Exception occured in parseDateFormat-"
					+ DataServiceConstants.DATE_FORMAT_ERROR_MSG);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,
					DataServiceConstants.DATE_FORMAT_ERROR_E1008,
					DataServiceConstants.ERROR_STATUS,
					DataServiceConstants.DATE_FORMAT_ERROR_MSG,
					EventsAppConstants.ERROR_UMSG_DATE_FORMAT);
		}    

	}

	public static long calculateDateDiff(Date startDate ,Date endDate) throws DataServiceException
	{

		long dateDiff;
		long daysDiff;
		dateDiff = endDate.getTime()-startDate.getTime();
		daysDiff = dateDiff/(24 * 60 * 60 * 1000); 

		LOGGER.info("Inside CalculateDateDiff  Date difference ::"+dateDiff+" days Difference ::"+daysDiff);
		return daysDiff;
	}
}

package com.geaviation.eds.service.events.app.constants;

public class EventsAppConstants {


	public static final String ERR_M_INVALID_ESN = "Invalid Engine Serial Number. Engine Serial Number should be alphanumeric having 5 to 10 characters"; 
	public static final String EVENT_EMPTY_RESULT = "Engine Event service returned an empty result";
	public static final String ESN_NUMBER_MISSING  = "ESN Number cannot be empty.Please provide ESN Number";
	public static final String ERROR_MSG_ESN_VALIDATION_FAILED  = "ESN Number validation failed";
	public static final String ERROR_SINGLE_UMSG_INVALID_ESN = "- is not an valid ESN number";


	//Events Domain error messages
	public static final String ERROR_UMSG_ENGINE_CURRENT_STATUS_EMPTY_LIST = "Sorry no data available for given esn under shopevents. Please try with valid esn";
	public static final String ERROR_MSG_ENGINE_CURRENT_STATUS_EMPTY_LIST = "Engine Current Status returned an empty list";
	public static final String ERROR_UMSG_ENGINE_EVENT_HISTORY_EMPTY_LIST = "No Events data available for the given request. Please try with valid input";
	public static final String ERROR_MSG_ENGINE_EVENT_HISTORY_EMPTY_LIST = "Engine Event history returned an empty list";
	public static final String ERROR_MSG_ENGINE_EVENT_HISTORY_MISSING_INPUT="ESN or (Engine Model and Event Date) is mandatory ";
	public static final String ERROR_MSG_ENGINE_EVENT_HISTORY_INPUT_VALIDATION="No Event Date available for Engine Model passed ";
	public static final String ERROR_MSG_ENGINE_EVENT_HISTORY_DETAILS_MISSING_INPUT="ESN or Event No or (Engine Model and Event Date range) is mandatory ";
	
	//Maintenance messages
	public static final String MISSING_EVENT_ID  = "Event Id is missing in the request";	
	public static final String MAINTENANCE_EVENT_EMPTY_LIST = "Maintenance Events returned an empty list";
	public static final String NO_OF_DAYS_LIMIT_FAILED  = "No of days should be less than or equal to 30"; 
	public static final String FORECAST_REMOVAL_EMPTY_LIST = "Forecast engines returned an empty list for the given input";
	public static final String SHOPVISIT_EVENT_EMPTY_LIST = "Shopvisit Events returned an empty list";
	public static final String EVENTS_INVALID_ENDDATE="The EndDate should be greater than FromDate";
	public static final String EVENTS_FUTUREDATE_SELECTION="The EndDate and FromDate should not be future date";
	public static final String EVENTS_WRONG_DATE_SELECTION="Please enter valid date";
	public static final String EVENTS_WRONG_DATE_FORMAT="Please enter date in yyyy-mm-dd format";
	public static final String EVENTS_ENDDATE_SELECTION="The EndDate should not more than 12 months from FromDate";
	public static final String EVENTS_DATE_VALIDATION = "Range between From date and To date must be one year ";
	public static final String UMSG_EVENTS_DATE_VALIDATION = " Valid Range between From date and To date must be less than or equal to one year and To date must be greater than From date ";
	public static final String ERROR_UMSG_DATE_FORMAT = "Given date format is wrong ,It should follow 'yyyy-mm-dd' format";
	
	public static final String  UI_MESSAGE_NO_INPUTS = "At least one input required.";
	public static final String  DESC_MESSAGE_INPUTS = "Problem occured while calling the service due to insufficient inputs";
	public static final String  DESC_MESSAGE_DATE_INPUTS = "If start date passed end date also needs to be passed";
	public static final String  UI_MESSAGE = "Oops some unexpected internal error, Please try again later or contact the Fleet Support Team at 1-877-432-3272 or 1-513-552-3272 or via email aviation.fleetsupport@ge.com ";
	public static final String  DESC_MESSAGE_DATA = "No Data Found";

	
	public static final String ENGINE_FAMILY="engineFamily";
	public static final String FLEET_ID="fleetId";
	public static final String START_DATE="startDate";
	public static final String END_DATE="endDate";
	
	public static final String ESN_SRC_TIMEPERIOD_MISSING  = "Insufficient request details. ESN,SRC,Time Period Start Date and Time Period End Dates are mandatory";
	public static final String ERROR_MSG_EVENTS_OVERLAY_EMPTY_LIST = "Events Overlay returned an empty list";
	public static final String EVENTS_INVALID_TIMEPERIOD_ENDDATE="The Time period End Date should be greater than Time period Start Date";
	public static final String EVENTS_DATE_VALIDATION_EXCEPTION="Date validation error,Please try again later or contact the Fleet Support Team at 1-877-432-3272 or 1-513-552-3272 or via email aviation.fleetsupport@ge.com";
	
	//Added constants for WWO maintenance events
	public static final String ESN = "esn";
	public static final String EVENTID = "eventid";
	public static final String EVENT_START_DATE = "eventStartDate";
	public static final String EVENT_END_DATE = "eventEndDate";
	
	public static final String ESN_MISSING = "esn is missing in the input";
	public static final String EVETN_ID_MISSING = "event id is missing in the input";
	public static final String EVENT_START_DATE_MISSING = "Event start date is missing in the input";
	public static final String EVENT_END_DATE_MISSING = "Event end date is missing in the input";
	public static final String CONSUMER_APP_MISSING = "Consumer app is missing in the header";
	
	
	private  EventsAppConstants(){
		
	}
}
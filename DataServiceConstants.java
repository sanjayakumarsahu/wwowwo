package com.geaviation.eds.service.common.constant;

/**
 * Constant declaration file
 *
 */
public class DataServiceConstants {

	public static final String STATUS_ERROR = "error";
	public static final String AND = " AND";
	public static final String UNDEFINED_NUMBER_VALUE = "0";
	public static final String ERROR_MSG_SEPERATOR = " - ";
	public static final String EMPTY_STRING = "";
	public static final String GE_USER_WSA = "WSA";
	public static final String Y = "Y";
	public static final String N = "N";

	//General    
	public static final String ERROR_STATUS  = "ERROR";
	public static final String SUCCESS_STATUS = "SUCCESS";
	public static final String PARTIAL_SUCCESS= "PARTIAL SUCCESS";
	public static final String ERROR_TYP_INFO = "Information";
	public static final String ERROR_TYP_WAR = "Warning";
	public static final String ERROR_TYP_CRICTICAL = "Critical";
	public static final String ERROR_TYP_SYSTEM = "System";
	public static final String ERROR_TYP_ERR = "Error";


	//Error Codes
	public static final String GENERIC_ERROR = "9999";
	public static final String MISSING_INPUT_ERROR_E1001 = "1001";
	public static final String DATA_VALIDATION_ERROR_E1002 = "1002";
	public static final String NO_DATA_FOUND_E1003 = "1003";
	public static final String DB_ERROR = "1004";
	public static final String ERROR_INVALID_PARAM_COMBINATION_E1005 = "1005";
	public static final String UNAUTHORIZED_E1006="1006";
	public static final String TECHNICAL_ERROR = "500";    
	public static final String DATE_FORMAT_ERROR_E1008 = "1008";
	public static final String NUMBER_FORMAT_EXCEPTION = "1009";
	public static final String NUMBER_FORMAT_ERR_MSG="Number format Exception";
	public static final String MISSING_INPUT_ERROR_E1010 ="1010";

	public static final String DATA_ACCESS_EXCEPTION  = "Data access exception occurred";    
	public static final String DATE_PARSE_EXCEPTION  = "Parse Exception in DAO";   
	public static final String MISSING_INPUT_MSG  = "Input is missing in the request";    
	public static final String MISSING_CONSUMERAPP_MSG  = "ConsumerApp is missing in the request";  
	public static final String SSO_AUTH_FAILED  = "SSO id is not available in the header";    
	public static final String SSO_PORTAL_ID_MANDATORY  = "SSO id and Portal id are mandatory";	
	public static final String WSA_AUTH_FAILED  = "GE USER is missing in the header";
	public static final String WSA_AUTH_INVALID  = "Invalid credentials passed in the header";
	public static final String DATE_FORMAT_ERROR_MSG = "Date Format Exception";
	public static final String WIDEGET_AUTH_FAILED  = "widgetName is not available in the form";  
	public static final String CSVID_AUTH_FAILED  = "csvid is not available";
	public static final String LIMIT_FAILED_MSG  = "Limit the offset size to less than 5000"; 
	public static final String FILTER_MISSING = "Filter is missing in the input";
	
	public static final String OFFSET_MISSING = "Offset is missing in the input";
	public static final String LIMIT_MISSING = "Limit is missing in the input";

	public static final String SUCCESS = "success";
	public static final String STATUS = "true";
	public static final String TRUE_VALUE = "true";

	public static final String COMMON_EXCEPTION_USER_MSG  = "Oops some unexpected internal error occured. Please contact support team";
	public static final String DATA_ACCESS_EXCEPTION_USER_MSG  = "Oops some unexpected DB error occured. Please contact support team";

	public static final String ESN_NUMBER_INVALID  = "Data not available for this ESN number - Please enter valid ESN";

	public static final String JUNIT_TEST ="Junit-Test";
	public static final String CONSUMER_APP="ConsumerApp";
	

	public static final String  SM_SSOID = "sm_ssoid";
	public static final String  PORTAL_ID = "portal_id";
	public static final String  CLIENT_ID = "clientId";
	public static final String  CLIENT_SECRET = "clientSecret";
	public static final String  SCOPE = "scope";
	public static final String  PING_URL = "pingUrl";
	public static final String  ACCESS_TOKEN="access_token";
	public static final String  EXPIRES_IN="expires_in";
	public static final String  REFRESH_TOKEN="refresh_token";
	public static final String  TOKEN_TYPE="token_type";
	public static final String  OAUTH_PING_ERROR = "Ping URL is invalid";
	public static final String  OAUTH_PING_INVALID_TOKEN_UMSG = "ExpiredOauthToken or InvalidOauthToken";
	public static final String  OAUTH_PING_INVALID_TOKEN_MSG = "ServerError or Unauthorizedrequest";
	public static final String  OAUTH_PING_INVALID_SERVER_DOWN_MSG="Internal Server Error";
	public static final String  OAUTH_PING_INVALID_SERVER_DOWN_UMSG="Backend Server Down";

	public static final String  OAUTH_PING_INVALID_BACKEND_PATH_UMSG="Backend Path or Sentry Path not found";
	public static final String  OAUTH_PING_INVALID_BACKEND_PATH_MSG="ServerError, The requested virtual directory was not found";
	public static final String  OAUTH_PING_INPUTPARAMS_ERROR = "Client ID or CLIENT_SECRET or SCOPE is invalid";

	public static final String  OAUTH_INPUT_VALIDATION = "Client ID / CLIENT_SECRET / SCOPE /PING URL should not be empty";
	public static final String STATUS_YES = "Y";
	public static final String WRONG_DATE_FORMAT="Please enter date in yyyy-mm-dd format";
	public static final String DATE_FORMAT_YYYY_MM_DD="yyyy-MM-dd";
	public static final String PERCENTILE ="%";
	public static final String DB_EXECUTION_TIME ="DB Query executed in  :";
	public static final String DB_CONNECTION ="Establish  DB connection : Success";
	public static final String FILTER_INVALID ="Data not available for this filter - Please enter valid filter";
	//Plotting
	public static final String PARAM_PHASE = "Parameter / Phase";
	public static final String TAIL_NUM_CONSTANT = "Tail Number";
	public static final String MSG_SEPERATOR = ",";
	public static final String ERROR_MSG_REQD_SUFFIX = " is a required input field";
	public static final String ESN_CONSTANT = "ESN";
	public static final String SOURCE_NPI = "NPI";
	public static final String ESN_SEPARATOR = "|";
	public static final String ESN_SEPARATOR_ENCODE = "%7C";
	public static final String ALL = "ALL";
	public static final String SMOOTHED = "_SMOOTHED";
	public static final String SMOOTHEDENDOFWORD = "_SMOOTHED$";
	public static final String PLOTTING_CONSTANT = "Plotting";
	public static final String LESSOR_CUSTOMER_TYPE = "LESSOR";
	public static final String UNPARSEABLE_DATE = "Unparseable Date";
	public static final String PORTAL_DEFAULT = "CWC";
	public static final String NO_ESN_FOR_TAIL = "Given tail number is not associated to an engine.";
	public static final String ERROR_NO_ESN_ACCESS = "No ESN access for user.";
	public static final String ENGINE_PARAMETER = "E";
	public static final String ERROR_NOT_AC_PARAM = "You have selected an engine parameter in settings and an aircraft in the navigation. \n Please select an aircraft parameter in settings or select an engine via the navigation above.";
	public static final String APP_NAME = "appName";
	public static final String OWNER_CODE = "ownerCode";
	public static final String PHASE_PARAMETER_COMBO = "phaseParameterCombo";
	public static final String PREVIOUS_INSTALLS = "previousInstalls";
	public static final String SSOID = "ssoId";
	public static final String TYPE_OF_DATA = "typeOfData";
	public static final String TIME_PERIOD_START = "timePeriodstart";
	public static final String TIME_PERIOD_END = "timePeriodend";
	public static final String DAYS_OF_DATA = "daysOfData";
	public static final String NPI = "npi";
	public static final String AUTHORIZED_ESN_FINAL = "authorizedESNFinal";
	public static final String PARAMETER_Y_FINAL = "parameterYFinal";
	public static final String PHASE_FINAL = "phaseFinal";
	public static final String TAIL_NUMBER_FINAL = "tailNumberfinal";
	public static final String AC_TYPE_FINAL = "acTypefinal";
	public static final String TIME_PERIOD_START_FINAL = "timePeriodstartFinal";
	public static final String TIME_PERIOD_END_FINAL = "timePeriodendFinal";
	public static final String OPERATOR_ORG_NM_FINAL = "operatorOrgNmfinal";
	public static final String SSO_ID_FINAL = "ssoIdFinal";
	public static final String ENGINE_POSITION_FINAL = "enginePositonfinal";
	public static final String PORTALID = "portalId";
	public static final int NTHREAD = 5;
	
	public static final String ESN = "esn";
	public static final String OP_FILTER = "opFilter";
	public static final String TERADATA_DATASOURCE = "Teradata";
	public static final String CASS_DATASOURCE = "cass";	
	

	public static enum EnumTypeOfData {
		RAWPOINT, RAWLINE,RAWPOINTSMOOTH, RAWLINESMOOTH, SMOOTH
	}
	private DataServiceConstants(){

	}


}

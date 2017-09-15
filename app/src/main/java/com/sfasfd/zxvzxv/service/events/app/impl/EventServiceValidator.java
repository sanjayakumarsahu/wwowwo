package com.geaviation.eds.service.events.app.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.geaviation.eds.service.common.constant.DataServiceConstants;
import com.geaviation.eds.service.common.exception.DataServiceException;
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
	private  static final String[] ERROR_ACTION_INVALID = {"Action should not be empty","Default Action - UNKNOWN"};
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
		if (value != null && !"".equalsIgnoreCase(value.trim()) && DATA_ELEMENT_ESN.equalsIgnoreCase(type) && (value.length() < ESN_MIN_LENGTH
				|| value.length() > ESN_MAX_LENGTH
				|| !value.matches(ESN_REGEX_PATTERN))) {

			LOGGER.error("Exception occured in validate-"+EventsAppConstants.ERROR_MSG_ESN_VALIDATION_FAILED);
			throw new DataServiceException(DataServiceConstants.ERROR_TYP_ERR,DataServiceConstants.MISSING_INPUT_ERROR_E1001,DataServiceConstants.ERROR_STATUS,EventsAppConstants.ERROR_MSG_ESN_VALIDATION_FAILED,value+EventsAppConstants.ERROR_SINGLE_UMSG_INVALID_ESN,ERROR_UMSG_INVALID_ESN);

		}
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
}


package com.geaviation.eds.service.common.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geaviation.eds.service.common.constant.DataServiceConstants;

@ControllerAdvice
public class DataServiceExceptionHandler  {

	private static final Logger LOGGER = Logger.getLogger(DataServiceExceptionHandler.class.getName());



	@ExceptionHandler({DataServiceException.class})
	@ResponseBody
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public DataServiceFault serviceError(HttpServletResponse httpRes,DataServiceException exception) {

		String errorCode = exception.getWsaFault().getErrorCode();

		if(errorCode.equalsIgnoreCase(DataServiceConstants.NO_DATA_FOUND_E1003)
				) {
			httpRes.setStatus(HttpStatus.OK.value());
		} 
		else if(errorCode.equalsIgnoreCase(DataServiceConstants.MISSING_INPUT_ERROR_E1001)
				|| errorCode.equalsIgnoreCase(DataServiceConstants.DATA_VALIDATION_ERROR_E1002)
				|| errorCode.equalsIgnoreCase(DataServiceConstants.DATE_FORMAT_ERROR_E1008)        		
				){

			httpRes.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		else if(errorCode.equalsIgnoreCase(DataServiceConstants.ERROR_INVALID_PARAM_COMBINATION_E1005)){
			httpRes.setStatus(HttpStatus.FORBIDDEN.value());
		}
		else if(errorCode.equalsIgnoreCase(DataServiceConstants.UNAUTHORIZED_E1006)){
			httpRes.setStatus(HttpStatus.UNAUTHORIZED.value());
		}

		else {
			httpRes.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		LOGGER.log(Level.SEVERE, "DataServiceException Caught in Handler - " + exception.getWsaFault().getErrorCode()+"-"+exception.getWsaFault().getErrorMessage()+"-"+exception.getWsaFault().getUserMessage());

		return exception.getWsaFault();
	}

}

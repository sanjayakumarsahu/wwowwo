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
public class DataServiceTechnicalExceptionHandler {

	private static final Logger LOGGER = Logger.getLogger(DataServiceTechnicalExceptionHandler.class.getName());



	@ExceptionHandler({DataServiceTechnicalException.class})
	@ResponseBody
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public DataServiceFault serviceError(HttpServletResponse httpRes,DataServiceTechnicalException exception) {

		httpRes.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		exception.getWsaFault().setStatus(DataServiceConstants.ERROR_STATUS);
		exception.getWsaFault().setType(DataServiceConstants.ERROR_TYP_CRICTICAL);
		exception.getWsaFault().setUserMessage(DataServiceConstants.DATA_ACCESS_EXCEPTION_USER_MSG);

		LOGGER.log(Level.SEVERE, "DataServiceTechnicalException Caught in Handler -"+exception.getMessage()+"-"+exception.getCause());

		return exception.getWsaFault();
	}	

}

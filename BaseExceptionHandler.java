package com.geaviation.eds.service.common.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.geaviation.eds.service.common.constant.DataServiceConstants;

public class BaseExceptionHandler implements ExceptionMapper<Exception>{

	private static final Logger LOGGER = Logger.getLogger(BaseExceptionHandler.class.getName());

	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public Response toResponse(Exception exception) {
		DataServiceFault wsFault = new DataServiceFault();

		ResponseBuilder respBuilder = null;
		respBuilder = Response.status(Status.INTERNAL_SERVER_ERROR);

		wsFault.setErrorCode(DataServiceConstants.GENERIC_ERROR);
		wsFault.setStatus(DataServiceConstants.ERROR_STATUS);
		wsFault.setType(DataServiceConstants.ERROR_TYP_SYSTEM);
		wsFault.setErrorMessage(exception.getMessage());
		wsFault.setUserMessage(DataServiceConstants.COMMON_EXCEPTION_USER_MSG);


		LOGGER.log(Level.SEVERE, "Exception Caught in Handler - "+exception);

		return respBuilder.entity(wsFault).build();
	}

}

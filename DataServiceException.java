package com.geaviation.eds.service.common.exception;

import java.io.Serializable;

import com.geaviation.eds.service.common.constant.DataServiceConstants;

/**
 * Data Service Exception class
 *
 */
public class DataServiceException extends Exception implements Serializable {

	private static final long serialVersionUID = -1L;
	private DataServiceFault wsaFault;
	
	public DataServiceException() {
		super();
	}

	public DataServiceException(String message, Exception exception) {
		super(exception.getMessage());
		this.wsaFault = new DataServiceFault(DataServiceConstants.GENERIC_ERROR, exception.getMessage());        
	}

	public DataServiceException(String errorCode,String message) {
		super(message);
		this.wsaFault = new DataServiceFault(errorCode, message);
	}

	//Refactoring in Fuse 6.0

	public DataServiceException(String errorCode) {
		super();
		this.wsaFault = new DataServiceFault(errorCode);
	}

	public DataServiceException(String type,String errorCode,String status,String message,String userMsg,String[] errors ) {
		super(message);
		this.wsaFault = new DataServiceFault(type,errorCode, status,message , userMsg, errors);
	}

	public DataServiceException(String type,String errorCode,String status,String message,String userMsg) {
		super(message);
		this.wsaFault = new DataServiceFault(type,errorCode, status,message , userMsg);
	}

	public DataServiceFault getWsaFault() {
		return wsaFault;
	}

	public void setWsaFault(DataServiceFault wsaFault) {
		this.wsaFault = wsaFault;
	}
}

package com.geaviation.eds.service.common.exception;

import java.io.Serializable;

public class DataServiceTechnicalException extends RuntimeException implements Serializable {


	private static final long serialVersionUID = 1L;
	private DataServiceFault wsaFault;

	public DataServiceTechnicalException() {
		super();

	}
	public DataServiceTechnicalException(String message, Throwable cause) {
		super(message, cause);

		this.wsaFault = new DataServiceFault(message, cause.getMessage());
	}
	public DataServiceTechnicalException(String message) {
		super(message);

	}
	public DataServiceTechnicalException(Throwable cause) {
		super(cause);

	}
	public DataServiceFault getWsaFault() {
		return wsaFault;
	}
	public void setWsaFault(DataServiceFault wsaFault) {
		this.wsaFault = wsaFault;
	}


}

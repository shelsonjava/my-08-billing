package com.info08.billing.callcenterbk.client.exception;

public class CallCenterException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public CallCenterException() {
		super();
	}

	public CallCenterException(String exception) {
		super(exception);
		this.errorMessage = exception;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}

package br.com.tasklist.service.exception;

public class BusinessRulesException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BusinessRulesException(String message) {
		super(message);
	}
}
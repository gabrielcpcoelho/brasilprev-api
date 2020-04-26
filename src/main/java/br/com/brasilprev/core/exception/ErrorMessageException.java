package br.com.brasilprev.core.exception;

public class ErrorMessageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8665315771399395294L;

	public ErrorMessageException() {
		super();
	}

	public ErrorMessageException(String s) {
		super(s);
	}

	public ErrorMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorMessageException(Throwable cause) {
		super(cause);
	}

}

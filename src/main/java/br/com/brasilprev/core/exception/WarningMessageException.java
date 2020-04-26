package br.com.brasilprev.core.exception;

public class WarningMessageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8665315771399395294L;

	public WarningMessageException() {
		super();
	}

	public WarningMessageException(String s) {
		super(s);
	}

	public WarningMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public WarningMessageException(Throwable cause) {
		super(cause);
	}

}

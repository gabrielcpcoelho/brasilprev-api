package br.com.brasilprev.core.exception;

public class NotAllowedExclusionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3414429795786605787L;

	public NotAllowedExclusionException() {
		super();
	}

	public NotAllowedExclusionException(String s) {
		super(s);
	}

	public NotAllowedExclusionException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAllowedExclusionException(Throwable cause) {
		super(cause);
	}

}

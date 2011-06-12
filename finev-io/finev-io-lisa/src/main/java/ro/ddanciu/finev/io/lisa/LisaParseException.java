package ro.ddanciu.finev.io.lisa;

import java.text.ParseException;

public class LisaParseException extends ParseException {

	private static final long serialVersionUID = 1L;

	public LisaParseException(String message, Throwable cause) {
		this(message);
		initCause(cause);
	}

	public LisaParseException(String message) {
		super(message, -1);
	}

}

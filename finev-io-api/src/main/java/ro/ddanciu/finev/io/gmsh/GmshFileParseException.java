/**
 * 
 */
package ro.ddanciu.finev.io.gmsh;

import java.text.ParseException;

/**
 * @author dan
 *
 */
public class GmshFileParseException extends ParseException {

	private static final long serialVersionUID = 1L;

	public GmshFileParseException(String message, Throwable cause) {
		this(message);
		initCause(cause);
	}

	public GmshFileParseException(String message) {
		super(message, -1);
	}
}

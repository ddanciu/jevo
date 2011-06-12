package ro.ddanciu.finev.io;

import java.io.PrintStream;

import ro.ddanciu.finite.elements.api.PoliLine;

public interface Writer {
	
	/**
	 * 
	 * @param where
	 * @param elements
	 */
	void write(PrintStream out, PoliLine... elements);

}

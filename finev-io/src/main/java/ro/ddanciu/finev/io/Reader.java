package ro.ddanciu.finev.io;

import java.io.BufferedReader;
import java.text.ParseException;

import ro.ddanciu.finev.io.gmsh.GmshFileParseException;
import ro.ddanciu.finite.elements.api.PoliLine;

public interface Reader {

	/**
	 * 
	 * @param in
	 * @return
	 * @throws GmshFileParseException 
	 */
	Iterable<PoliLine> read(BufferedReader in) throws ParseException;
	
}

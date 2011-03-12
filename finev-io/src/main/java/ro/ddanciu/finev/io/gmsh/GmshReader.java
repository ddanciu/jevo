package ro.ddanciu.finev.io.gmsh;

import java.io.BufferedReader;

import ro.ddanciu.finev.io.Reader;
import ro.ddanciu.finite.elements.api.PoliLine;

public class GmshReader implements Reader {

	@Override
	public Iterable<PoliLine> read(BufferedReader in) throws GmshFileParseException {
		GmshFile file = new GmshFile();
		file.read(in);
		return file.elements();
	}

}

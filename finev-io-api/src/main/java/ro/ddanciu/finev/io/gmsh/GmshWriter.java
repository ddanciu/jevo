/**
 * 
 */
package ro.ddanciu.finev.io.gmsh;

import java.io.PrintStream;

import ro.ddanciu.finev.io.Writer;
import ro.ddanciu.finite.elements.api.PoliLine;

/**
 * @author dan
 *
 */
public class GmshWriter implements Writer {

	@Override
	public void write(PrintStream out, PoliLine... elements) {

		GmshFile file = new GmshFile();
		for (PoliLine pl : elements) {
			file.addElement(pl);
		}
		
		file.write(out);
		
	}
	
	
	

}

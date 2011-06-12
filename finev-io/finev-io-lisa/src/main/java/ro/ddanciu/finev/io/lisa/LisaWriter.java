package ro.ddanciu.finev.io.lisa;

import java.io.PrintStream;

import ro.ddanciu.finev.io.Writer;
import ro.ddanciu.finite.elements.api.PoliLine;

public class LisaWriter implements Writer {

	@Override
	public void write(PrintStream out, PoliLine... triangulation) {
		try {
			LisaFile file = new LisaFile();
			for (PoliLine element : triangulation) {
				file.add(element);
			}
			file.write(out);
		} catch (LisaParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

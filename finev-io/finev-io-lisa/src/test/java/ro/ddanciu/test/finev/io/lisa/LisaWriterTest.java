package ro.ddanciu.test.finev.io.lisa;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ro.ddanciu.finev.io.Writer;
import ro.ddanciu.finev.io.lisa.LisaWriter;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Triangle;

public class LisaWriterTest {
	
	private PrintStream out;
	private Writer writer;
	
	@Before
	public void init() {
		writer = new LisaWriter();
		out = mock(PrintStream.class);
	}

	@Test
	public void triangle() {
		Triangle echilateral = new Triangle(
				new Point(new BigDecimal("0"),		new BigDecimal("1.73205081")), 
				new Point(new BigDecimal("-1.5"), 	new BigDecimal("-0.86602540")),
				new Point(new BigDecimal("1.5"), 	new BigDecimal("-0.86602540")));
		
		writer.write(out, echilateral);
		
		verify(out).println("<liml>");
		verify(out).println("<node nid=\"1\" x=\"0.0000\" y=\"1.7321\" />");
		verify(out).println("<node nid=\"2\" x=\"-0.7500\" y=\"0.4330\" />");
		verify(out).println("<node nid=\"3\" x=\"-1.5000\" y=\"-0.8660\" />");
		verify(out).println("<node nid=\"4\" x=\"0.0000\" y=\"-0.8660\" />");
		verify(out).println("<node nid=\"5\" x=\"1.5000\" y=\"-0.8660\" />");
		verify(out).println("<node nid=\"6\" x=\"0.7500\" y=\"0.4330\" />");
		verify(out).println("<elset name=\"Default Group\" >");
		verify(out).println("<elem eid=\"1\" shape=\"tri6\" nodes=\"1 2 3 4 5 6\" />");
		verify(out).println("</elset>");
		verify(out).println("</liml>");

	}

}

package ro.ddanciu.test.finev.io.gmsh;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ro.ddanciu.finev.io.Reader;
import ro.ddanciu.finev.io.gmsh.GmshReader;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.PoliLine;
import ro.ddanciu.finite.elements.api.Triangle;

public class GmshReaderTest {
	
	private Reader reader;
	private BufferedReader in;
	
	
	@Before
	public void init() {
		reader = new GmshReader();
		in = mock(BufferedReader.class);
	}

	@Test(expected = NullPointerException.class)
	public void nullReader() throws ParseException {
		reader.read(null);
	}
	
	
	@Test(expected = ParseException.class)
	public void emptyReader() throws ParseException, IOException {
		when(in.readLine()).thenReturn("").thenReturn(null);
		
		reader.read(in);
	}

	@Test
	public void triangle() throws IOException, ParseException {
		
		when(in.readLine())
		.thenReturn("$MeshFormat")
		.thenReturn("2.0 0 8")
		.thenReturn("$EndMeshFormat")
		.thenReturn("$Nodes")
		.thenReturn("3")
		.thenReturn("1 0.0 0.0 0.0")
		.thenReturn("2 0.0 1.0 0.0")
		.thenReturn("3 3.0 1.0 0.0")
		.thenReturn("$EndNodes")
		.thenReturn("$Elements")
		.thenReturn("1")
		.thenReturn("1  2 2 99 2 1 2 3")
		.thenReturn("$EndElements")
		.thenReturn(null);
		
		Iterator<PoliLine> actual = reader.read(in).iterator();
		
		Assert.assertTrue(actual.hasNext());
		
		PoliLine triangle = actual.next();
		
		Triangle expected = new Triangle(new Point(0, 0), new Point(0, 1), new Point(3, 1));
		Assert.assertEquals("Got something else!", expected, triangle);
		
	}

	@Test
	public void triangles() throws IOException, ParseException {
		
		when(in.readLine())
		.thenReturn("$MeshFormat")
		.thenReturn("2.0 0 8")
		.thenReturn("$EndMeshFormat")
		.thenReturn("$Nodes")
		.thenReturn("4")
		.thenReturn("1 0.0 0.0 0.0")
		.thenReturn("2 0.0 1.0 0.0")
		.thenReturn("3 3.0 1.0 0.0")
		.thenReturn("4 3.0 0.0 0.0")
		.thenReturn("$EndNodes")
		.thenReturn("$Elements")
		.thenReturn("2")
		.thenReturn("1 2 2 99 2 1 2 3")
		.thenReturn("2 2 2 99 2 4 2 3")
		.thenReturn("$EndElements")
		.thenReturn(null);
		
		Iterator<PoliLine> received = reader.read(in).iterator();
		
		Assert.assertTrue(received.hasNext());
		PoliLine triangle1 = received.next();
		
		Assert.assertTrue(received.hasNext());
		PoliLine triangle2 = received.next();

		Set<PoliLine> actual = new HashSet<PoliLine>();
		actual.add(triangle1);
		actual.add(triangle2);
		
		
		Set<PoliLine> expected = new HashSet<PoliLine>();
		expected.add(new Triangle(new Point(0, 0), new Point(0, 1), new Point(3, 1)));
		expected.add(new Triangle(new Point(3, 0), new Point(0, 1), new Point(3, 1)));
		
		Assert.assertEquals("Got something else!", expected, actual);
		
	}
}

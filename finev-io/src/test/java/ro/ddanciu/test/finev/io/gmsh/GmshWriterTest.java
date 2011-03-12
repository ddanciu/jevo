package ro.ddanciu.test.finev.io.gmsh;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import ro.ddanciu.finev.io.Writer;
import ro.ddanciu.finev.io.gmsh.GmshWriter;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Triangle;

public class GmshWriterTest {
	
	private PrintStream out;
	private Writer writer;
	
	@Before
	public void init() {
		writer = new GmshWriter();
		out = mock(PrintStream.class);
	}

	@Test
	public void triangle() {
		writer.write(out, new Triangle(new Point(0, 0), new Point(0, 1), new Point(1, 0)));

		verify(out).println("$MeshFormat");
		verify(out).println("2.2 0 8");
		verify(out).println("$EndMeshFormat");
		verify(out).println("$Nodes");
		verify(out).println(3);                    
		verify(out).println("1 0.0000 0.0000 0.0000");
		verify(out).println("2 0.0000 1.0000 0.0000");
		verify(out).println("3 1.0000 0.0000 0.0000");
		verify(out).println("$EndNodes");
		verify(out).println("$Elements");
		verify(out).println(1); // one element:_
		verify(out).println("1 2 2 99 2 1 2 3"); // triangle #1: type 3, physical 99, elementary 2, nodes 1 2 3
		verify(out).println("$EndElements");
	}

	@Test
	public void triangles() {
		writer.write(out, 
				new Triangle(new Point(0, 0), new Point(0, 1), new Point(1, 0)),
				new Triangle(new Point(1, 0), new Point(0, 1), new Point(1, 1)));

		verify(out).println("$MeshFormat");
		verify(out).println("2.2 0 8");
		verify(out).println("$EndMeshFormat");
		verify(out).println("$Nodes");
		verify(out).println(4);                    
		verify(out).println("1 0.0000 0.0000 0.0000");
		verify(out).println("2 0.0000 1.0000 0.0000");
		verify(out).println("3 1.0000 0.0000 0.0000");
		verify(out).println("4 1.0000 1.0000 0.0000");
		verify(out).println("$EndNodes");
		verify(out).println("$Elements");
		verify(out).println(2); // one element:_
		verify(out).println("1 2 2 99 2 1 2 3"); // triangle #1: type 3, physical 99, elementary 2, nodes 1 2 3
		verify(out).println("2 2 2 99 2 3 2 4"); // triangle #1: type 3, physical 99, elementary 2, nodes 1 2 3
		verify(out).println("$EndElements");
	}
	
}

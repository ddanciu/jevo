package ro.ddanciu.finev.io.lisa;

import static java.lang.String.format;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.PoliLine;
import ro.ddanciu.finite.elements.api.Segment;

/**
 * 
 * <code>
 * 
 * ﻿<liml>
 *  <node nid="1" x="0" y="0" z="0" />
 *  <node nid="2" x="2" y="0" z="0" />
 *  <node nid="3" x="4" y="0" z="0" />
 *  <node nid="4" x="0" y="0.25" z="0" />
 *  <node nid="5" x="2" y="0.25" z="0" />
 *  <node nid="6" x="4" y="0.25" z="0" />
 *  <node nid="7" x="0" y="0.5" z="0" />
 *  <node nid="8" x="2" y="0.5" z="0" />
 *  <node nid="9" x="4" y="0.5" z="0" />
 *  <elset name="Default Group">
 *    <elem eid="1" shape="tri6" nodes="1 2 3 6 9 5" />
 *    <elem eid="2" shape="tri6" nodes="1 5 9 8 7 4" />
 *  </elset>
 * </liml>
 * 
 * </code>
 * @author dan
 *
 */
public class LisaFile {
	
	private static final String ELEMENTS_GROUP_NAME = "Default Group";
	
	private HashMap<Point, Integer> points = new LinkedHashMap<Point, Integer>();

	private List<Element> elements = new ArrayList<Element>();

	private int elementsCounter = 1;

	public void add(PoliLine line) throws LisaParseException {
		Element el = create(line);
		elements.add(el);
	}
	
	/**
	 * Assure definition of the point in the file.
	 * @param point the {@link Point} to be defined.
	 * @return the id of the point within this file.
	 */
	public int add(Point point) throws LisaParseException {
		Integer id = points.get(point);
		if (id == null) {
			id = points.size() + 1;
			Integer old = points.put(point, id);
			assert old == null;
		}
		return id;
		
	}

	public void write(PrintStream ps) {
		ps.println("<liml>");
		
		for(Map.Entry<Point, Integer> entry : points.entrySet()) {
			Integer id = entry.getValue();
			Point point = entry.getKey();
			
			ps.println(format("<node nid=\"%d\" x=\"%.4f\" y=\"%.4f\" />", id, point.getX(), point.getY()));
		}
		
		ps.println(format("<elset name=\"%s\" >", ELEMENTS_GROUP_NAME));
		
		for (Element element : elements) {
			Integer id = element.getId();
			ElementType type = element.getType();
			String nodes = StringUtils.join(element.getNodes(), " ");
			
			ps.println(format("<elem eid=\"%d\" shape=\"%s\" nodes=\"%s\" />", id, type, nodes));
		}
		
		ps.println("<elset>");
		ps.println("</liml>");
	}

	private Element create(PoliLine line) throws LisaParseException {
		switch (line.size()) {
		case 3: {
			return newTriangle6Elment(line);
		}
		default:
			throw new LisaParseException(format("Unknown element for PoliLine: %s", line));	
		}
	}
	
	
	private Element newTriangle6Elment(PoliLine line) throws LisaParseException {
		
		assert line.size() == 3 : "Wrong input PoliLine for newTriangle6Elment";
		
		Integer[] pointRefs = new Integer[6];
		
		int counter = 0;
		for (Segment s : line.segments()) {
			pointRefs[counter++] = add(s.getP1());
			pointRefs[counter++] = add(s.getMiddlePoint());
		}
		return new Element(elementsCounter ++, ElementType.tri6, pointRefs);
	}

	private static enum ElementType {
		tri6
	}
	
	private class Element {

		private final int id;
		
		private final ElementType type;
		
		private final Integer[] nodes;

		public Element(int id, ElementType type, Integer[] nodes) {
			this.id = id;
			this.type = type;
			this.nodes = nodes;
		}

		public int getId() {
			return id;
		}

		public ElementType getType() {
			return type;
		}

		public Integer[] getNodes() {
			return nodes;
		}
		
	}
}

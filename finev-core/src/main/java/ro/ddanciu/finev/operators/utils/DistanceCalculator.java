package ro.ddanciu.finev.operators.utils;

import java.math.BigDecimal;

import ro.ddanciu.finite.elements.api.Point;

public interface DistanceCalculator {

	public abstract BigDecimal compute(Point x);

}
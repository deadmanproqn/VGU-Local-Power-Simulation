package vgu.generator;

import java.util.ArrayList;

import interfaces.AbstractComponent;

/***
 * 
 * Empty template to generate a Generator Factory This Factory should return one
 * or several of the self-implemented Power-Supplies, derived from
 * 'interfaces.AbstractComponent'. It is a convenient place to implement
 * distribution and frequency of different generator types.
 *
 */
public class GeneratorFactory {
	public GeneratorFactory() {
	}

	public static AbstractComponent generate(String name, double maxPower, double minPower, double maxChange,
			double minChange) {
		GeneratorFactory f = new GeneratorFactory();
		return f.new SingleGenerator(name, maxPower, minPower, maxChange, minChange);

	}

	/**
	 * 
	 * The nextGaussian() method returns random numbers with a mean of 0 and a
	 * standard deviation of 1. to change the mean (average) of the distribution, we
	 * add the required value; to change the standard deviation, we multiply the
	 * value.
	 * 
	 * @param amount   Nr. of Values
	 * @param avgPower Mean
	 * @param std      Standard Deviation
	 */
	public static ArrayList<AbstractComponent> generate(int amount, double totalPower, double startPower) {
		// TODO
		return null;
	}
}

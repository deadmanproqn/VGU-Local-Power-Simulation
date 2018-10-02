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
public class GeneratorFactory extends AbstractComponent {
	private double power;

	GeneratorFactory() {
	}

	GeneratorFactory(String name, double maxPower, double minPower, double maxChange, double minChange) {
		this.name = name;
		setMaxPower(maxPower);
		setMinPower(minPower);
		setMaxChange(maxChange);
		setMinChange(minChange);
		this.power = minPower;
	}

	public void next() {
	};

	public String getName() {
		return name;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		if (this.power /* minPower */ == /* avgPower */power) {
			return;
		}

		// absolute difference > maxChange
		if (Math.abs(this.power/* minPower */ - /* avgPower */power) > getMaxChange()) {
			this.power += getMaxChange();
		}
		// absolute difference < minChange
		else if (Math.abs(this.power/* minPower */ - /* avgPower */power) < getMinChange()) {
			this.power = (power + getMinChange());
		}
		// min <= abs diff <= max
		else {
			this.power = power;
		}

		// check the power after setting to keep it in the range [minPower, maxPower]
		if (this.power > getMaxPower()) {
			this.power = getMaxPower();
		} else if (this.power < getMinPower()) {
			this.power = getMinPower();
		}
	}

	public double getCost() {
		double coefficient = .5, maxPower = getMaxPower(), maxChange = getMaxChange(), minChange = getMinChange();

		coefficient += maxPower < 2500 ? .25 : 0;
		coefficient += maxChange > .5 * maxPower ? .25 : 0;
		coefficient += minChange < .5 * maxPower ? .25 : 0;

		return coefficient * getPower();
	}

	// generate a generator
	public static AbstractComponent generate(String name, double maxPower, double minPower, double maxChange,
			double minChange) {
		return new GeneratorFactory(name, maxPower, minPower, maxChange, minChange);
	}

	// generate a list of generators
	public static ArrayList<AbstractComponent> generate(int numGen, double totalPower, double initialPower) {
		ArrayList<AbstractComponent> generator = new ArrayList();
		double avgPower = totalPower / numGen;
		for (int i = 0; i < numGen; ++i) {
			generator.add(new GeneratorFactory("Generator_" + i, avgPower, 0.0, avgPower, avgPower));
			if (initialPower > avgPower) {
				// Generator g = (Generator) generator.get(i);
				// System.out.println(g.getName());
				generator.get(i).setPower(avgPower);
				initialPower -= avgPower;
			}
		}
		return generator;
	}

	// public void print() {
	// ArrayList<AbstractComponent> al = generate(10, 500, 200);
	// for (AbstractComponent c : al) {
	// Generator g = (Generator) c;
	// System.out.println(g.getName()+" "+c.getMaxPower());
	// }
	// }
	//
	// public static void main(String[] args) {
	// Generator g1 = new Generator();
	// g1.print();
	// //Generator g2 = new Generator("G2", 2000, 100, 1, 1);
	// //System.out.println(g2.getCost());
	// }
}
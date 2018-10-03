package vgu.generator;

import interfaces.AbstractComponent;
import java.util.ArrayList;

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
		double maxPower = getMaxPower();
		double minPower = getMinPower();
		double maxChange = getMaxChange();
		double minChange = getMinChange();

		if (power >= minPower && power <= maxPower) {
			// calculate the difference between new power and original power
			double diffPower = (this.power > power) ? this.power - power : power - this.power;
			// new power and original power are the same
			if (diffPower == 0) {
				return;
			}

			// new power and original power are different
			// check if diffPower is in the range of minChange and maxChange
			if (diffPower > maxChange) {
				if (this.power > power) {
					this.power -= maxChange;
				} else {
					this.power += maxChange;
				}
			} else if (diffPower < minChange) {
				if (this.power > power) {
					this.power -= minChange;
				} else {
					this.power += minChange;
				}
			} else {
				this.power = power;
				return;
			}

			// check if newly assigned power in the range of minPower and maxPower
			if (this.power > maxPower) {
				this.power = maxPower;
			} else if (this.power < minPower) {
				this.power = minPower;
			}
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
	public static ArrayList<AbstractComponent> generate(int numGen, double totalMaxPower, double initialPower) {
		ArrayList<AbstractComponent> generator = new ArrayList();
		double avgMaxPower = totalMaxPower / numGen;
		for (int i = 0; i < numGen; ++i) {
			generator.add(new GeneratorFactory("Generator_" + i, avgMaxPower, 0.0, avgMaxPower, avgMaxPower));
			if (initialPower > avgMaxPower) {
				generator.get(i).setPower(avgMaxPower);
				initialPower -= avgMaxPower;
			}
		}
		return generator;
	}
}
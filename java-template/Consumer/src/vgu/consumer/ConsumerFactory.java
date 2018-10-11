package vgu.consumer;

import interfaces.AbstractComponent;
import java.util.ArrayList;

// -MUST be registered at the Control Model :NOT DONE
// -MUST have an On and Off state,each with defined power: DONE
// -MAY be registered as cluster with other consumers(e.g.households): DONE
// -MUST provide an interface to measure the power: DONE
// -MUST provide an interface for remote changes: DONE
// -MAY change state due to internal or external conditions (e.g. time, weather,
// price ..): OPTIONAL
/***
 * This is an empty template to implement a Consumer Factory. This Factory
 * should return one or several of the self-implemented Consumers, derived from
 * 'interfaces.AbstractComponent' It is a convenient place implement
 * distribution and frequency of consumer types.
 *
 */
public class ConsumerFactory extends AbstractComponent {
	// default run pattern
	static double[] run_patt = new double[] { .5, .2, .15, .45, .75, .60, .55, .40, .45, .65, .95, .75 };
	// static double[] run_heavy = new double[] { .5, .5, .5, 1, 1, 1, 1, 1, 1, 1,
	// .5, .5 };
	private double power;
	private int iteration = -1;
	private boolean state = false;

	// class constructor
	public ConsumerFactory() {
	}

	// contructor with custom run pattern
	/**
	 * 
	 * @param name
	 * @param maxPower
	 * @param minPower
	 * @param maxChange
	 * @param minChange
	 */

	public ConsumerFactory(String name, double maxPower, double minPower, double maxChange, double minChange) {
		this.name = name;
		this.setMinChange(minChange);
		this.setMaxChange(maxChange);
		this.setMinPower(minPower);
		this.setMaxPower(maxPower);
		this.power = minPower;
		this.next();
	}

	// this method set consummer run behavior
	public static void setRunBehaviour(double[] run_patt) {
		if (run_patt.length != 12) {
			throw new IndexOutOfBoundsException("The Run Pattern is not compatible with this program. Please try again");
		}
		
		ConsumerFactory.run_patt = run_patt;
	}

	/**
	 * Generate a singe consumer with custom run Pattern
	 * 
	 * @param name
	 * @param maxPower
	 * @param minPower
	 * @param maxChange
	 * @param minChange
	 * @return
	 */
	public static AbstractComponent generate(String name, double maxPower, double minPower, double maxChange,
			double minChange) {
		// call the factory
		// to generate a consumer
		return new ConsumerFactory(name, maxPower, minPower, maxChange, minChange);
	}

	/**
	 * 
	 * Generate a set of consumers with custom run pattern.
	 * 
	 * @param amount        Nr. of consumers to generate
	 * @param avg_max_Power Mean maximun Power of <amount> consumers
	 * @param deviation     Standard Deviation of power
	 * @return
	 */
	public static ArrayList<AbstractComponent> generate(int amount, int avg_max_Power, int deviation) {
		// this is the list of consummer
		ArrayList<AbstractComponent> consumers = new ArrayList<AbstractComponent>();
		// for every consumer
		for (int i = 1; i <= amount; i++) {
			// generate a value equal avgPower + r*Deviation.
			// r is from -1.0 to 1.0
			double val_max = (-1 + Math.random() * (1 - (-1))) + (double) avg_max_Power;
			// add a consummer with said data
			consumers.add(new ConsumerFactory("c" + i, val_max, 0, val_max, val_max));

		}
		return consumers;
	}

	@Override
	public double getPower() {
		return this.power;
	}

	/**
	 * @param power
	 * @return
	 */
	@Override
	public void setPower(double power) {
		if (this.power != power) {
			if (Math.abs(this.power - power) > this.getMaxChange()) {
				if (power > this.power) {
					this.power += this.getMaxChange();
				} else {
					this.power -= this.getMaxChange();
				}
			} else if (Math.abs(this.power - power) < this.getMinChange()) {
				if (power > this.power) {
					this.power = power + this.getMinChange();
				} else {
					this.power = power - this.getMinChange();
				}
			} else {
				this.power = power;
			}

			if (this.power > this.getMaxPower()) {
				this.power = this.getMaxPower();
			} else if (this.power < this.getMinPower()) {
				this.power = this.getMinPower();
			}

		}

	}

	@Override
	public void next() {
		// the folowing iteration simulate the state of the consumer is active or not
		// base on the consumtion pattern.
		this.iteration++;
		if (Math.random() < ConsumerFactory.run_patt[this.iteration]) {
			this.state = true;
			this.power = this.getMaxPower();
		} else {
			this.state = false;
			this.power = this.getMinPower();
		}
		// if the consumer is active, set max power, otherwise, set min power

	}

	public void next(boolean state) {
		this.iteration++;
		this.state = state;
		if (!state) {
			this.power = 0;
		}
	}

	@Override
	public double getCost() {
		return this.power;
	}

}
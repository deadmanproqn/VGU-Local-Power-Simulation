package vgu.consumer;

import interfaces.AbstractComponent;
import java.util.ArrayList;
import java.util.Random;

// -MUST be registered at the Control Model :NOT DONE
// -MUST have an On and Off state,each with defined power: need config
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
public class ConsumerFactory {
	// class constructor
	public ConsumerFactory() {
	}

	/**
	 * Stores the amount of consumers running at every iteration in percentage
	 */
	static double[] run = new double[] { .5, .2, .15, .45, .75, .60, .55, .40, .45, .65, .95, .75 };

	// this method set consummer run behavior
	public static void setRunBehaviour(double[] runTime) {
		run = runTime;
	}

	/**
	 * Generate a singe consumer
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
		ConsumerFactory f = new ConsumerFactory();
		// to generate a consumer
		return f.new SingleComponent(name, maxPower, minPower, maxChange, minChange, run);
		// TODO
	}

	/**
	 * 
	 * Generate a set of consumers.
	 * 
	 * @param amount   Nr. of consumers to generate
	 * @param avgPower Mean Power of <amount> consumers
	 * @param std      Standard Deviation of power
	 */
	public static ArrayList<AbstractComponent> generate(int amount, int avgPower, int std) {
		// new instance to generate
		ConsumerFactory f = new ConsumerFactory();
		// a random instance for power calculation
		Random r = new Random();
		// this is the list of consummer
		ArrayList<AbstractComponent> consumers = new ArrayList<>();
		// for every consumer
		for (int i = 1; i < amount; i++) {
			// generate a value equal avgPower + r*Deviation.
			// r is from -1.0 to 1.0
			double val = r.nextGaussian() * (double) std + (double) avgPower;
			// generate a consummer with said data
			ConsumerFactory.SingleComponent c = f.new SingleComponent("c" + i, val, 0, val, val, run);
			// and add it to the array
			consumers.add(c);
		}

		// TODO

		return null;
	}

	// this class represent a single consumer
	class SingleComponent extends AbstractComponent {
		private double power;
		private String name;
		private double[] running;
		private int iteration = -1;
		private boolean state = false;

		public SingleComponent(String name, double maxPower, double minPower, double maxChange, double minChange,
				double[] running) {
			this.running = running;
			this.name = name;
			this.setMinChange(minChange);
			this.setMaxChange(maxChange);
			this.setMinPower(minPower);
			this.setMaxPower(maxPower);
			this.power = minPower;
			this.next();
		}

		@Override
		public double getPower() {
			return this.power;
		}

		@Override
		public void setPower(double power) {
			if (this.power != power) {
				if (Math.abs(this.power - power) > this.getMaxChange()) {
					this.power += this.getMaxChange();
				} else if (Math.abs(this.power - power) < this.getMinChange()) {
					this.power = power + this.getMinChange();
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
			// the folowing iteration simulate the state of the consumer is active or not base on the consumtion pattern.
			++this.iteration;
			Random r = new Random();
			if (r.nextDouble() < this.running[this.iteration]) {
				this.state = true;
			} else {
				this.state = false;
			}
			//if the consumer is active, set max power, otherwise, set min power
			if (this.state) {
				this.power = this.getMaxPower();
			} else {
				this.power = this.getMinPower();
			}

		}

		@Override
		public double getCost() {
			return this.power;
		}

	}

}
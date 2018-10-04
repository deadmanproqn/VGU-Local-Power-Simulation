package vgu.control;

import interfaces.AbstractComponent;
import interfaces.IControl;

import java.util.ArrayList;
import java.util.List;

/**
 * This empty control template can be best implemented by evaluating all
 * JUnit-TestCases.
 */
public class Control implements IControl {
	ArrayList<AbstractComponent> generators = new ArrayList<AbstractComponent>();
	ArrayList<AbstractComponent> consumers = new ArrayList<AbstractComponent>();
	int overload = 0;
	int blackout = 0;

	@Override
	public void addGenerator(AbstractComponent generator) {
		generators.add(generator);
	}

	@Override
	public void removeGenerator(AbstractComponent generator) {
		generators.remove(generator);
	}

	@Override
	public List<AbstractComponent> getGenerators() {
		return generators;
	}

	@Override
	public void addConsumer(AbstractComponent consumer) {
		consumers.add(consumer);
	}

	@Override
	public void removeConsumer(AbstractComponent consumer) {
		consumers.remove(consumer);
	}

	@Override
	public List<AbstractComponent> getConsumers() {
		return consumers;
	}

	@Override
	public double getTotalDemand() {
		double demand = 0;
		for (AbstractComponent c : consumers) {
			demand += c.getPower();
		}
		return demand;
	}

	@Override
	public double getTotalSupply() {
		double supply = 0.0D;
		for (AbstractComponent g : generators) {
			supply += g.getPower();
		}
		return supply;
	}

	@Override
	public double getFrequency() {
		double diff = (getTotalDemand() - getTotalSupply()) / Math.max(getTotalDemand(), getTotalSupply());
		return 50.0D - diff * 10;
	}

	@Override
	public double getCost() {
		double cost = 0;
		for (AbstractComponent g : generators) {
			cost += g.getCost();
		}
		return cost;
	}

	@Override
	public double getProfit() {
		return getTotalDemand();
	}

	@Override
	public void nextIteration() {
		double changeAmount = 0;
		double diff = getTotalSupply() - getTotalDemand();
		double sign = diff / Math.abs(diff);
		for (AbstractComponent g : generators) {
			changeAmount = changeAmount(diff, g) * sign;
			g.setPower(g.getPower() - changeAmount);
			diff -= changeAmount;
		}

		double frequency = getFrequency();
		checkFrequency(frequency);
	}

	private double changeAmount(double diff, AbstractComponent g) {
		double availableChange = 0;

		if (diff > 0) {
			availableChange = Math.min(g.getMaxChange(), g.getPower() - g.getMinPower());
		} else if (diff < 0) {
			availableChange = Math.min(g.getMaxChange(), g.getMaxPower() - g.getPower());
		}
		if (availableChange < g.getMinChange()) {
			availableChange = 0;
		} else if (Math.abs(diff) < availableChange) {
			availableChange = Math.max(Math.abs(diff), g.getMinChange());
		}
		return availableChange;
	}

	private void checkFrequency(double frequency) {
		if (frequency > 51) {
			int x = (int) Math.ceil(generators.size() * 0.1);
			for (int i = 0; i < x; i++) {
				generators.remove(0);
			}
			blackout = 0;
			overload += 1;
			// System.out.println("Overload!");
		} else if (frequency < 49) {
			int x = (int) Math.ceil(consumers.size() * 0.15);
			for (int i = 0; i < x; i++) {
				consumers.remove(0);
			}
			blackout += 1;
			overload = 0;
			// System.out.println("Blackout!");
		} else {
			blackout = 0;
			overload = 0;
		}

		if ((overload == 3) || (blackout == 3)) {
			consumers.clear();
			generators.clear();
			// System.out.println("Defect!");
		}
	}
}

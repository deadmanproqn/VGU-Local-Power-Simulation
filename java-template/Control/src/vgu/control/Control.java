package vgu.control;
import java.util.List;
import interfaces.AbstractComponent;
import interfaces.IControl;

/**
 * This empty control template can be best implemented by evaluating all
 * JUnit-TestCases. 
 */
public class Control implements IControl {

	@Override
	public void addGenerator(AbstractComponent generator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeGenerator(AbstractComponent generator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AbstractComponent> getGenerators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addConsumer(AbstractComponent consumer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeConsumer(AbstractComponent consumer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AbstractComponent> getConsumers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getTotalDemand() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTotalSupply() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getFrequency() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getProfit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void nextIteration() {
		// TODO Auto-generated method stub
		
	}

}

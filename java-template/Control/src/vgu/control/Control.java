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
	int overDemand = 0;
	int underDemand = 0;
	
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
		double result = 0.0D;
	    for (AbstractComponent c : consumers) {
	      result += c.getPower();
	    }
	    return result;
	}

	@Override
	public double getTotalSupply() {
		double result = 0.0D;
	    for (AbstractComponent g : generators) {
	      result += g.getPower();
	    }
	    return result;
	}

	@Override
	public double getFrequency() {
		double consume = getTotalDemand();
	    double generation = getTotalSupply();
	    double diff = (consume - generation)/Math.max(consume, generation);
	    
	    return 50.0D - diff*10;
	}

	@Override
	public double getCost() {
		double result = 0.0D;
	    for (AbstractComponent g : generators) {
	      result += g.getCost();
	    }
	    return result;
	}

	@Override
	public double getProfit() {
		double result = 0.0D;
	    for (AbstractComponent g : consumers) {
	      result += g.getCost();
	    }
	    return result;
	}

	@Override
	public void nextIteration() {
		double demand = getTotalDemand();
	    double onePercent = demand / 100.0D;
	    double supply = getTotalSupply();
	    double diff = supply - demand;
	    double availableChange = 0.0D;
	    
	    for (int i = 0; i < generators.size(); i++) {
	    	AbstractComponent g = (AbstractComponent)generators.get(i);
	    	if (diff > 0.0D) {
	    		availableChange = Math.min(g.getMaxChange(), g.getPower() - g.getMinPower());
	    		if (diff > availableChange) {
	    			g.setPower(g.getPower() - availableChange);
	    			diff -= availableChange;
	    			if (diff < onePercent) {
	    				break;
	    			}
	    		} else {
	    			availableChange = Math.max(diff, g.getMinChange());
	    			if (availableChange <= Math.abs(diff * 2.0D)) {
	    				g.setPower(g.getPower() - availableChange);
	    				diff -= availableChange;
	    				if (diff < onePercent) {
	        			break;
	    				}
	        	}
	        }
	      }
	      if (diff < 0.0D) {
	    	  availableChange = Math.min(g.getMaxChange(), g.getMaxPower() - g.getPower());
	    	  if (Math.abs(diff) > availableChange) {
	    		  g.setPower(g.getPower() + availableChange);
	    		  diff += availableChange;
	    	  }
	    	  else {
	    		  availableChange = Math.max(Math.abs(diff), g.getMinChange());  
	    		  if (availableChange <= Math.abs(diff * 1.5D)) {
	    			  g.setPower(g.getPower() + availableChange);
	    			  diff += availableChange;
	    			  if (Math.abs(diff) < onePercent) {
	    				  break;
	    			  }
	    		  }
	    	  }
	      }
	    }
	    double frequency = getFrequency();
	    frequencyRequirements(frequency);
	}
	private void frequencyRequirements(double frequency) {
		if (frequency > 51.0D) {
			int count = (int)Math.ceil(generators.size() / 100.0D * 10.0D);
		    for (int i = 0; i < count; i++) {
		    	generators.remove(0);
		    }
		    underDemand += 1;
		    overDemand = 0;
		    //System.out.println("Overload");
		}
		else if (frequency < 49.0D) {
			int count = (int)Math.ceil(consumers.size() / 100.0D * 15.0D);
		    for (int i = 0; i < count; i++) {
		    	consumers.remove(0);
		    }
		    overDemand += 1;
		    underDemand = 0;
		    //System.out.println("Blackout");
		}
		else {
		    overDemand = 0;
		    underDemand = 0;
		}
		if ((overDemand == 3) || (underDemand == 3)) {
		    consumers.clear();
		    generators.clear();
		    //System.out.println("Defect");
		}
	}
	
	//new
	/*
	 *Them 1 abstract void setState(), 1 String getName() vao AbstractComponent
	 *public void setState() {
			if(state) {
				state = false;
				power = getMinPower();
			}
			else {
				state = true;
				power = getMaxPower();
			}
		}
	 */
	// public void ConsumerStateChange(String name) {
	// 	for (AbstractComponent c : consumers) {
	// 		if (c.getName().equals(name)) {
	// 			c.setState();
	// 		}
	// 	}
	// }
	/*
	 * Them private boolean state = true; vao Generator
	 */
	// public void GeneratorStateChange(String name) {
	// 	for (AbstractComponent g : generators) {
	// 		if (g.getName().equals(name)) {
	// 			g.setState();
	// 		}
	// 	}
	// }
}

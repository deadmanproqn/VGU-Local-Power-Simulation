package gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import vgu.consumer.ConsumerFactory;
import vgu.control.Control;
import vgu.generator.GeneratorFactory;
import java.util.ArrayList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;


import interfaces.AbstractComponent;
/**
 * Class to control the GUI (LineChart.fml file built by Java Scene Builder
 * @author Kelvin
 *
 */
public class ChartController implements Initializable{
	//Variables to display on line chart
	XYChart.Series generatorGUI;
	XYChart.Series consumerGUI;
	XYChart.Series frequencyGUI;
	String iter;
	boolean setFlag = false;
	//Variable for generating scenario 1
    @FXML
    private Button scenOne;
    //Variable for generating scenario 2
    @FXML
    private Button sceneTwo;
    
    @FXML
    private Button resetButton;
    
    @FXML
    private CategoryAxis iteration;
    
    @FXML
    private CategoryAxis frequencyIteration;
    
    @FXML
    private NumberAxis totalFrequency;

    @FXML
    private NumberAxis totalPower;
    
    @FXML
    private LineChart<?,?> lineChart;

    @FXML
    private LineChart<?, ?> lineChart2;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    void generateScene1(ActionEvent event) {
    	generatorGUI = new XYChart.Series();
    	consumerGUI = new XYChart.Series();
    	frequencyGUI = new XYChart.Series();
    	
    	generatorGUI.setName("Generator Case 1");
    	consumerGUI.setName("Consumer Case 1");
    	frequencyGUI.setName("Frequency Case 1");
    	
    	ConsumerFactory.setRunBehaviour(new double[]{.5,.2,.15,.45,.75,.60,.55,.40,.45,.65,.95,.75});
    	Control control = new Control();
    	ArrayList<AbstractComponent> consumer;
    	consumer = ConsumerFactory.generate(100, 100, 0);
    	for(AbstractComponent a : consumer) {			
    		control.addConsumer(a);
    	}
    	ArrayList<AbstractComponent> generator;
    	generator = GeneratorFactory.generate(15, 11000, 5000);
    	for(AbstractComponent a : generator) {			
    		control.addGenerator(a);
    	}	
    	consumerGUI.getData().add(new XYChart.Data("1",control.getTotalDemand()));
    	generatorGUI.getData().add(new XYChart.Data("1",control.getTotalSupply()));
        frequencyGUI.getData().add(new XYChart.Data("1",control.getFrequency()));

    	for(int i = 0; i < 11; i++) {
    		for (AbstractComponent c : consumer) {
    			c.next();
    		}			
    		control.nextIteration();
            consumerGUI.getData().add(new XYChart.Data(iter.valueOf(i+2),control.getTotalDemand()));
            generatorGUI.getData().add(new XYChart.Data(iter.valueOf(i+2),control.getTotalSupply()));
            frequencyGUI.getData().add(new XYChart.Data(iter.valueOf(i+2),control.getFrequency()));    			
		

    	}
    	lineChart.getData().addAll(generatorGUI,consumerGUI);
    	lineChart2.getData().addAll(frequencyGUI);
    }

    @FXML
    void generateScene2(ActionEvent event) {
    	generatorGUI = new XYChart.Series();
    	consumerGUI = new XYChart.Series();
    	frequencyGUI = new XYChart.Series();
    	
    	generatorGUI.setName("Generator Case 2");
    	consumerGUI.setName("Consumer Case 2");
    	frequencyGUI.setName("Frequency Case 2");
    	Control control = new Control();
		
		ArrayList<AbstractComponent> consumer;		
		consumer = ConsumerFactory.generate(100, 100, 0);
		
		for(AbstractComponent a : consumer) {			
			control.addConsumer(a);
		}
		ArrayList<AbstractComponent> generator;
		generator = GeneratorFactory.generate(6, 10000, 5000);
		for(AbstractComponent a : generator) {			
			control.addGenerator(a);
		}
	
    	consumerGUI.getData().add(new XYChart.Data("1",control.getTotalDemand()));
    	generatorGUI.getData().add(new XYChart.Data("1",control.getTotalSupply()));
    	frequencyGUI.getData().add(new XYChart.Data("1",control.getFrequency()));
    	System.out.println(control.getTotalDemand() +" "+ control.getTotalSupply()+ " "+ control.getFrequency());
		for(int i = 0; i < 11; i++) {
			if(i==2) {
				generator.remove(0);
				generator.remove(0);
			}
			//System.out.println("Frequency: "+control.getFrequency()+"Hz Demand: "+control.getTotalDemand()+"W  Supply: "+control.getTotalSupply()+"W");
			for (AbstractComponent c : consumer) {
				c.next();
			}			
			control.nextIteration();
    		//consumerGUI.getData().add(new XYChart.Data(iter.valueOf(i+2),i++));
    		//generatorGUI.getData().add(new XYChart.Data(iter.valueOf(i+2),i++));
    		//frequencyGUI.getData().add(new XYChart.Data(iter.valueOf(i+2),i++));
    		consumerGUI.getData().add(new XYChart.Data(iter.valueOf(i+2),control.getTotalDemand()));
    		generatorGUI.getData().add(new XYChart.Data(iter.valueOf(i+2),control.getTotalSupply()));
    		frequencyGUI.getData().add(new XYChart.Data(iter.valueOf(i+2),control.getFrequency()));
    		System.out.println(control.getTotalDemand() +" "+ control.getTotalSupply()+ " "+ control.getFrequency());
    	
		}
		lineChart.getData().addAll(generatorGUI,consumerGUI);	
		lineChart2.getData().addAll(frequencyGUI);
		
    }
    
    @FXML
    void resetData(ActionEvent event) {
    	lineChart.getData().clear();
    	lineChart2.getData().clear();
    }
}


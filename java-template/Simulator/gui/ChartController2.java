package gui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import vgu.consumer.ConsumerFactory;
import vgu.control.Control;
import vgu.generator.GeneratorFactory;
import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import interfaces.AbstractComponent;
/**
 * Class to control the GUI (LineChart.fml file built by Java Scene Builder
 * @author Viet
 *
 */
public class ChartController2 implements Initializable{
	//Variables to display on line chart
	XYChart.Series<String,Number> generatorGUI;
	XYChart.Series<String,Number> consumerGUI;
	XYChart.Series<String,Number> frequencyGUI;
	ArrayList<String> infoList = new ArrayList<String>();
	/**
	ArrayList<AbstractComponent> storeGen = new ArrayList<AbstractComponent>();
	ArrayList<AbstractComponent> storeCon = new ArrayList<AbstractComponent>();
	*/
	String iter;
	boolean setFlag = false;

	
	Control control = new Control();
	ArrayList<AbstractComponent> consumer;
	ArrayList<AbstractComponent> generator;
	double graphInfo[][] = new double[12][3];
	int itr=-1;
	//Variable for generating scenario 1
    @FXML
    private Button scenOne;
    //variable for generating scenario 2
    @FXML
    private Button sceneTwo;
    
    @FXML
    private Label displayStat;
    
    @FXML
    private Button printButton;
    
    @FXML
    private CategoryAxis iteration;
    
    @FXML
    private CategoryAxis frequencyIteration;
    
    @FXML
    private NumberAxis totalFrequency;

    @FXML
    private NumberAxis totalPower;
    
    @FXML
    private LineChart<String,Number> lineChart;

    @FXML
    private LineChart<String,Number> lineChart2;

    @FXML
    private TextField myTextField;
    
    @FXML 
    private Label consumerInfo;
    
    @FXML
    private Label generatorInfo;
    
    @FXML
    private Label generalInfo;
    //buttons
    @FXML private Button nextIteration;
    @FXML private Button remGen;
    @FXML private Button remCon;
    @FXML private Button setOnButton;
    @FXML private Button setOffButton; 
    //text fields
    @FXML private TextField iRemGen;
    @FXML private TextField iRemCon;
    @FXML private TextField iSetOn;
    @FXML private TextField iSetOff;
    
    @FXML private Label errMsg;
    @FXML private Label currentItr;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	errMsg.setText("");
		clearIterationData();
		numCon.setText(null);
		numGen.setText(null);
    }

    @FXML
    void removeGenerator(ActionEvent event) {
    	errMsg.setText(null);
    	try {
    		if (iRemGen.getText().isEmpty()) {
    			errMsg.setText("Please input data!");
    		} else {
    			int number = Integer.parseInt(iRemGen.getText());
    			if(number<=control.getGenerators().size()) {
	    			removeGenerator(number);
	    			iRemGen.clear();
	    			showGenAndCon();
    			}else {
    				errMsg.setText("Value is larger than size!");
    				iRemGen.clear();
    			}
    		}
    	} catch (Exception e) {
    		errMsg.setText("Input must be Integer!");
    	}
    }

    @FXML
    void removeConsumer(ActionEvent event) {
    	errMsg.setText(null);
    	try {
    		if (iRemCon.getText().isEmpty()) {
    			errMsg.setText("Please input data!");
    		} else {
    			int number = Integer.parseInt(iRemCon.getText());
    			if(number<=control.getConsumers().size()) {
	    			removeConsumer(number);
	    			iRemCon.clear();
	    			showGenAndCon();
    			}else {
    				errMsg.setText("Value is larger than size!");
    				iRemCon.clear();
    			}
    		}
    	} catch (Exception e) {
    		errMsg.setText("Input must be Integer!");
    	}    	
    }
    @FXML
    void generateScene1(ActionEvent event) {
    	if(itr==-1) {
	    	ConsumerFactory.setRunBehaviour(new double[]{.5,.2,.15,.45,.75,.60,.55,.40,.45,.65,.95,.75});
	    	//initialize data for line chart
	    	generatorGUI = new XYChart.Series<String,Number>();
	    	consumerGUI = new XYChart.Series<String,Number>();
	    	frequencyGUI = new XYChart.Series<String,Number>();
	    	generatorGUI.setName("Generator");
	    	consumerGUI.setName("Consumer");
	    	frequencyGUI.setName("Frequency");    	
	    	//intialize input
	

	    	consumer = ConsumerFactory.generate(100, 100, 0);
	    	generator = GeneratorFactory.generate(15, 11000, 5000);
	    	//run the first iteration
	    	for(AbstractComponent a : consumer) {			
	    		control.addConsumer(a);
	    	}
	    	for(AbstractComponent a : generator) {			
	    		control.addGenerator(a);
	    	}	
	    	//show iteration data
	    	showIterationData();
	    	//storing first iteration's data
	    	graphInfo[0][0] = control.getTotalDemand();
	    	graphInfo[0][1] = control.getTotalSupply();
	    	graphInfo[0][2] = control.getFrequency();
	        infoList.add("Consumers: "+ consumer.size() + "\nGenerators: " + generator.size());
	    	consumerGUI.getData().add(new XYChart.Data("1",control.getTotalDemand()));
	    	generatorGUI.getData().add(new XYChart.Data("1",control.getTotalSupply()));
	        frequencyGUI.getData().add(new XYChart.Data("1",control.getFrequency()));
	    	lineChart.getData().addAll(generatorGUI,consumerGUI);
	    	lineChart2.getData().addAll(frequencyGUI);
	    	
    	}
    	itr++;
    }


    @FXML
    void generateScene2(ActionEvent event) {
    	if(itr==-1) {
	    	ConsumerFactory.setRunBehaviour(new double[]{.5,.2,.15,.45,.75,.60,.55,.40,.45,.65,.95,.75});
	    	//initialize data for line chart
	    	generatorGUI = new XYChart.Series<String,Number>();
	    	consumerGUI = new XYChart.Series<String,Number>();
	    	frequencyGUI = new XYChart.Series<String,Number>();
	    	generatorGUI.setName("Generator");
	    	consumerGUI.setName("Consumer");
	    	frequencyGUI.setName("Frequency");    	
	    	//intialize input
	    	consumer = ConsumerFactory.generate(100, 100, 0);
	    	generator = GeneratorFactory.generate(15, 11000, 5000);
	    	//run the first iteration
	    	for(AbstractComponent a : consumer) {			
	    		control.addConsumer(a);
	    	}
	    	for(AbstractComponent a : generator) {			
	    		control.addGenerator(a);
	    	}	
	    	//show iteration data
	    	showIterationData();
	    	//storing first iteration's data
	    	graphInfo[0][0] = control.getTotalDemand();
	    	graphInfo[0][1] = control.getTotalSupply();
	    	graphInfo[0][2] = control.getFrequency();
	        infoList.add("Consumers: "+ consumer.size() + "\nGenerators: " + generator.size());
	    	consumerGUI.getData().add(new XYChart.Data("1",control.getTotalDemand()));
	    	generatorGUI.getData().add(new XYChart.Data("1",control.getTotalSupply()));
	        frequencyGUI.getData().add(new XYChart.Data("1",control.getFrequency()));
	    	lineChart.getData().addAll(generatorGUI,consumerGUI);
	    	lineChart2.getData().addAll(frequencyGUI);
			for(int i = 0; i < 11; i++) {
				if(i==2) {
					generator.remove(0);
					generator.remove(0);
				}
			}
	    	
    	}
    	itr++;
    }
    @FXML
    void nextStep(ActionEvent event) {
    	//if(control.getConsumers().isEmpty() && control.getGenerators().isEmpty()) {
    		
    	//}else{		
	    	if(itr>=11) {
	    		System.out.println("Nothing's left to show!");
	    	}else{
	    		clearIterationData();
	    		showIterationData();
		    	generatorGUI = new XYChart.Series<String,Number>();
		    	consumerGUI = new XYChart.Series<String,Number>();
		    	frequencyGUI = new XYChart.Series<String,Number>();
		    	generatorGUI.setName("Generator");
		    	consumerGUI.setName("Consumer");
		    	frequencyGUI.setName("Frequency"); 
	    		for (AbstractComponent c : consumer) {
	    			c.next();
	    		}			
	    		control.nextIteration();
	    		lineChart.getData().clear();
	    		lineChart2.getData().clear();
		    	graphInfo[itr+1][0] = control.getTotalDemand();
		    	graphInfo[itr+1][1] = control.getTotalSupply();
		    	graphInfo[itr+1][2] = control.getFrequency();
		        //infoList.add("Consumers: "+ consumer.size() + "\nGenerators: " + generator.size());
	            infoList.add("Consumers: "+ control.getConsumers().size() + "\nGenerators: " + control.getGenerators().size()); 
	            for(int i = 0; i < itr+2; i++) {
	    	    	consumerGUI.getData().add(new XYChart.Data(iter.valueOf(i+1),graphInfo[i][0]));
	    	    	generatorGUI.getData().add(new XYChart.Data(iter.valueOf(i+1),graphInfo[i][1]));
	    	        frequencyGUI.getData().add(new XYChart.Data(iter.valueOf(i+1),graphInfo[i][2]));           	
	            }
		    	lineChart.getData().addAll(generatorGUI,consumerGUI);
		    	lineChart2.getData().addAll(frequencyGUI);
		    	itr++;	
    		}
    	//}
    }    
    @FXML
    void printData(ActionEvent event) {
    	lineChart.getData().clear();
    	lineChart2.getData().clear();
    	ArrayList<String> infoList = new ArrayList<String>();
    	control.getConsumers().clear();
    	control.getGenerators().clear();
    	double graphInfo[][] = new double[12][3];
    	clearIterationData();
    	itr=-1;	
    }

    @FXML
    void displayInfo(ActionEvent event) {

    }
    @FXML
    void showFullList(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("InitialTable.fxml"));
			Parent root = (Parent) loader.load();
	    	ShowTable show = new ShowTable();
	    	show = loader.getController();
			show.retrieveData(control);
			show.viewData();
    		Stage stage = new Stage();
    		stage.setScene(new Scene(root));
    		stage.show();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    @FXML private Button backButton;    
    @FXML
    void backToInput(ActionEvent event) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();    	
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("InputData.fxml"));
			Parent root = (Parent) loader.load();
	        InputController inputController = new InputController();
    		Stage Inputstage = new Stage();
    		Inputstage.setScene(new Scene(root));
    		Inputstage.show();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}

    }

    /**
     * 
     * @param event Set number of consumers off
     * 
     */
    @FXML
    void setConsumerOff(ActionEvent event) {
    	errMsg.setText(null);
    	try {
    			if(control.getConsumers().size()==0) {
    				errMsg.setText("Consumer list is empty!");
    			}else {
	    			int size = Integer.parseInt(iSetOff.getText());
	    			if(size<0 || size > getActiveConsumer()) {
	    				errMsg.setText("Must be in range of activeCon");
	    			}else {
		    			setStatusConsumer(size);
		    			showIterationData();
		    			iSetOff.clear();
	    			}
    			}
    		}catch(Exception e) {
    		e.printStackTrace();
    		errMsg.setText("Invalid Input");
    	}
    	
    }
    @FXML Label activeCon;
    @FXML Label showIteration;
    @FXML Label showFrequency;
    @FXML Label showDemand;
    @FXML Label showSupply;
    @FXML Label showBalance;
    @FXML Label showCost;
    @FXML Label showProfit;
    @FXML Label numGen;
    @FXML Label numCon;
    //show generators and consumers
    void showGenAndCon() {
    	numCon.setText(Integer.toString(control.getConsumers().size()));
    	numGen.setText(Integer.toString(control.getGenerators().size()));
    }
    //show data of each iteration
    void showIterationData() {
    	if(itr+2==12) {
    		showCost.setText(Double.toString(control.getCost()));
    		showProfit.setText(Double.toString(control.getProfit()));
    	}
    	showIteration.setText(Integer.toString(itr+2));
    	showFrequency.setText(Double.toString(control.getFrequency()));
    	showDemand.setText(Double.toString(control.getTotalDemand()));
    	showSupply.setText(Double.toString(control.getTotalSupply()));
    	showBalance.setText(Double.toString(control.getBalance()));
    	activeCon.setText(Integer.toString(getActiveConsumer()));
    	showGenAndCon();
    }
    //clear data of that iteration
    void clearIterationData() {
    	showIteration.setText(null);
    	showFrequency.setText(null);
    	showDemand.setText(null);
    	showSupply.setText(null);
    	showBalance.setText(null);
    	showCost.setText(null);
    	showProfit.setText(null);
    	numCon.setText(null);
    	numGen.setText(null);
    	activeCon.setText(null);
    }
    //get iteration
    public int getIteration() {
    	return itr;
    }
    //get boolean
    public boolean getControl() {
    	return setFlag;
    }
    //get consumer list
    public void addConsumerList(ArrayList<AbstractComponent> myList) {
    	for(AbstractComponent i: myList) {
    		control.addConsumer(i);
    		
    	}
    	consumer = myList;
    	//System.out.println(consumer.size());

    }
    //get generator list
    public void addGeneratorList(ArrayList<AbstractComponent> myList) {
    	for(AbstractComponent i: myList) {
    		control.addGenerator(i);
    	}
    	generator = myList;
    }
    //remove consumer
    public void removeConsumer(int i) {
    	for(int j=0;j<i;j++) {
    		control.getConsumers().remove(0);
    	}
    }
    //remove generator
    public void removeGenerator(int i) {
    	for(int j=0;j<i;j++) {
    		control.getGenerators().remove(0);
    	}
    }
    //initialize first point
    public void drawGraph() {
    	ConsumerFactory.setRunBehaviour(new double[]{.5,.2,.15,.45,.75,.60,.55,.40,.45,.65,.95,.75});
    	generatorGUI = new XYChart.Series<String,Number>();
    	consumerGUI = new XYChart.Series<String,Number>();
    	frequencyGUI = new XYChart.Series<String,Number>();
    	generatorGUI.setName("Generator");
    	consumerGUI.setName("Consumer");
    	frequencyGUI.setName("Frequency");       	
    	consumerGUI.getData().add(new XYChart.Data("1",control.getTotalDemand()));
    	generatorGUI.getData().add(new XYChart.Data("1",control.getTotalSupply()));
        frequencyGUI.getData().add(new XYChart.Data("1",control.getFrequency()));
    	lineChart.getData().addAll(generatorGUI,consumerGUI);
    	lineChart2.getData().addAll(frequencyGUI);
    	System.out.println(control.getTotalSupply());
   
    	showIterationData();
    	showGenAndCon();
     	itr++;
    }
    //set on/off consumer
    public void setStatusConsumer(int amount) {
    	Random random = new Random();
    	int size = control.getConsumers().size();
    	int randomNumber = (int)random.nextInt(size);
    	control.getConsumers().get(randomNumber).setPower(-1);
    	for(int i=0; i<amount-1; i++) {
    		randomNumber = (int)random.nextInt(size);
    		ConsumerFactory con = (ConsumerFactory)control.getConsumers().get(randomNumber);
    		while(con.getStatus().equals("Off")) {
    			randomNumber = (int)random.nextInt(size);	
    			con = (ConsumerFactory)control.getConsumers().get(randomNumber);
    		}
    		control.getConsumers().get(randomNumber).setPower(-1);
    	}
    }
    //count active consumer
    public int getActiveConsumer() {
    	int number = 0, j=0;
    	ArrayList<ConsumerFactory> con = new ArrayList<ConsumerFactory>();
    	for(AbstractComponent i: control.getConsumers()) {
    		con.add((ConsumerFactory)i);
    	}
    	for(AbstractComponent i: con) {
    		if(con.get(j++).getStatus().equals("On")) {
    			number++;
    		}
    	}
    	return number;
    }

    /**
    //storing
    public void storeCon() {
    	for(AbstractComponent i: control.getConsumers()) {
    		storeCon.add(i);
    	}
    	System.out.println(storeCon.size());
    }
    public void storeGen() {
    	for(AbstractComponent i: control.getGenerators()) {
    		storeGen.add(i);
    	}
    }
    */
}


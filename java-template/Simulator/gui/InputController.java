package gui;

import interfaces.AbstractComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import vgu.consumer.ConsumerFactory;
import vgu.control.Control;
import vgu.generator.GeneratorFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InputController implements Initializable{

	private String Con_Name, Gen_Name;
	private int num_Gen, num_Con;
	private double Con_MaxPower, Con_MinPower, Con_MaxChange, Con_MinChange,
					Gen_MaxPower, Gen_MinPower, Gen_MaxChange, Gen_MinChange,
					total_GenPower, initial_Power, total_ConPower, _deviation;
	ArrayList<AbstractComponent> consumers;
	ArrayList<AbstractComponent> generators;
	Control control = new Control();
	TableView table = new TableView();
    @FXML
    private TextField ConMinPower;

    @FXML
    private Button singleGenEnter;

    @FXML
    private TextField initialPower;

    @FXML
    private TextField numCon;

    @FXML
    private Button multiConEnter;

    @FXML
    private TextField deviation;

    @FXML
    private TextField totalGenPower;

    @FXML
    private TextField GenMinPower;

    @FXML
    private TextField totalConPower;

    @FXML
    private Button multiGenEnter;

    @FXML
    private TextField ConMinChange;

    @FXML
    private TextField ConMaxPower;

    @FXML
    private TextField numGen;

    @FXML
    private TextField ConName;

    @FXML
    private Button singleConEnter;

    @FXML
    private TextField ConMaxChange;

    @FXML
    private TextField GenMaxPower;

    @FXML
    private TextField GenMaxChange;

    @FXML
    private TextField GenMinChange;

    @FXML
    private TextField GenName;
    
    @FXML
    private Label singleConLabel;

    @FXML
    private Label singleGenLabel;
    
    @FXML
    private Label multiGenLabel;
    
    @FXML
    private Label multiConLabel;
    
    @FXML private Label totalConsumer;
    @FXML private Label totalGenerator;
    //start simulation
    @FXML private Button startButton;
    @FXML private Button showButton;
    @FXML private Button clearButton;
    //table view
    @FXML
    private TableView<AbstractComponent> tableView;
    @FXML
    private TableColumn<AbstractComponent, Double> minChange;
    @FXML
    private TableColumn<AbstractComponent, Double> minPower;
    @FXML
    private TableColumn<AbstractComponent, Double> maxChange;
    @FXML
    private TableColumn<AbstractComponent, String> name;
    @FXML
    private TableColumn<AbstractComponent, Double> maxPower;
    @FXML
    private TableColumn<AbstractComponent, String> status;

    @FXML private Label simulation;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	name.setCellValueFactory(new PropertyValueFactory<AbstractComponent,String>("Name"));
    	maxPower.setCellValueFactory(new PropertyValueFactory<AbstractComponent,Double>("MaxPower"));
    	minPower.setCellValueFactory(new PropertyValueFactory<AbstractComponent,Double>("MinPower"));
    	maxChange.setCellValueFactory(new PropertyValueFactory<AbstractComponent,Double>("MaxChange"));
    	minChange.setCellValueFactory(new PropertyValueFactory<AbstractComponent,Double>("MinChange"));    	
    	status.setCellValueFactory(new PropertyValueFactory<AbstractComponent,String>("Status")); 
    }
    //<columnResizePolicy><TableView fx:constant="CONSTRAINED_RESIZE_POLICY" /></columnResizePolicy>
    @FXML
    void showAllList(ActionEvent event) {
    	showList();
    }
    
    void showList() {
    	tableView.getItems().clear();
        ObservableList<AbstractComponent> list = getUserList();
        tableView.getItems().addAll(list); 	
        //String number = ""+control.getConsumers().size();
        totalConsumer.setText(Integer.toString(control.getConsumers().size()));
       // number = ""+control.getGenerators().size();
        totalGenerator.setText(Integer.toString(control.getGenerators().size()));    	
    }
    private ObservableList<AbstractComponent> getUserList(){
    	ArrayList<AbstractComponent> myList = new ArrayList<AbstractComponent>();
    	for(AbstractComponent i: control.getConsumers()) {
    		myList.add(i);
    	}
    	for(AbstractComponent i: control.getGenerators()) {
    		myList.add(i);
    	}
    	ObservableList<AbstractComponent> list = FXCollections.observableArrayList(myList);
        return list;    	
    }
    
    @FXML void clearData(ActionEvent event) {
    	control.getConsumers().clear();
    	control.getGenerators().clear();   
    	showList();
    }
    @FXML
    void singleConEnter(ActionEvent event) {
    	singleConLabel.setText(null);
    	try {
    		if (ConName.getText().trim().isEmpty()) {
        		throw new Exception("empty name");
        	} else {
        		Con_Name = (String)ConName.getText();
        	}
	    	Con_MaxPower = Double.parseDouble(ConMaxPower.getText());
	    	Con_MinPower = Double.parseDouble(ConMinPower.getText());
	    	Con_MaxChange = Double.parseDouble(ConMaxChange.getText());
	    	Con_MinChange = Double.parseDouble(ConMinChange.getText());
	    	clearTextfield(ConName);
	    	clearTextfield(ConMaxPower);
	    	clearTextfield(ConMinPower);
	    	clearTextfield(ConMaxChange);
	    	clearTextfield(ConMinChange);
    		simulation.setText(null);
	    	control.addConsumer(new ConsumerFactory(Con_Name,Con_MaxPower,Con_MinPower,Con_MaxChange,Con_MinChange));
	    	showList();
    	} catch (Exception e) {
    		System.out.println(e);
    		singleConLabel.setText("Invalid input");
    	}
    }

    @FXML
    void multiConEnter(ActionEvent event) {
    	multiConLabel.setText(null);
    	try {
    		num_Con = Integer.parseInt(numCon.getText());
        	total_ConPower = Double.parseDouble(totalConPower.getText());
        	_deviation = Double.parseDouble(deviation.getText());
        	clearTextfield(numCon);
        	clearTextfield(totalConPower);
        	clearTextfield(deviation);
    		consumers = ConsumerFactory.generate(num_Con, (int)total_ConPower, (int)_deviation);		
    		for(AbstractComponent a : consumers) {			
    			control.addConsumer(a);
    		}
    		simulation.setText(null);
    		showList();
    	} catch (Exception e) {
    		System.out.println(e);
    		multiConLabel.setText("Invalid input");
    	}
    	
    }

    @FXML
    void singleGenEnter(ActionEvent event) {
    	singleGenLabel.setText(null);
    	try {
    		if (GenName.getText().isEmpty()) {
    			throw new Exception("empty name");
    		} else {
    			Gen_Name = GenName.getText();
    		}
    		Gen_MaxPower = Double.parseDouble(GenMaxPower.getText());
        	Gen_MinPower = Double.parseDouble(GenMinPower.getText());
        	Gen_MaxChange = Double.parseDouble(GenMaxChange.getText());
        	Gen_MinChange = Double.parseDouble(GenMinChange.getText());
        	clearTextfield(GenName);
        	clearTextfield(GenMaxPower);
        	clearTextfield(GenMinPower);
        	clearTextfield(GenMaxChange);
        	clearTextfield(GenMinChange);
    		simulation.setText(null);
        	control.addGenerator(new GeneratorFactory(Con_Name,Con_MaxPower,Con_MinPower,Con_MaxChange,Con_MinChange));
        	showList();
    	} catch (Exception e) {
    		System.out.println(e);
    		singleGenLabel.setText("Invalid input");
    	}
    }

    @FXML
    void multiGenEnter(ActionEvent event) {
    	multiGenLabel.setText(null);
    	try {
    		num_Gen = Integer.parseInt(numGen.getText());
        	total_GenPower = Double.parseDouble(totalGenPower.getText());
        	initial_Power = Double.parseDouble(initialPower.getText());
        	clearTextfield(numGen);
        	clearTextfield(totalGenPower);
        	clearTextfield(initialPower);
    		generators = GeneratorFactory.generate(num_Gen,(int)total_GenPower,(int)initial_Power);		
    		for(AbstractComponent a : generators) {			
    			control.addGenerator(a);
    		}
    		simulation.setText(null);
    		showList();
    	} catch (Exception e) {
    		System.out.println(e);
    		multiGenLabel.setText("Invalid input");
    	}
    }
    
    @FXML
    void startSimulation(ActionEvent event) {
    	if(control.getConsumers().isEmpty() && control.getGenerators().isEmpty()) {
    		simulation.setText("No data input");
    	}else {
            Stage stageStart = (Stage) startButton.getScene().getWindow();
            stageStart.close(); 
	    	try {   	

	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("LineChart2.fxml"));
	    		Parent root = (Parent) loader.load();
	    		ChartController2 chartController = new ChartController2();
	    		chartController = loader.getController();
	    		chartController.addConsumerList(getConsumerList());
	    		chartController.addGeneratorList(getGeneratorList());
	    		chartController.drawGraph();
	    		Stage stage = new Stage();
	    		stage.setScene(new Scene(root));
	    		stage.show();
	    	}catch(IOException e) {
	    		e.printStackTrace();
	    	}
    	}
    }    
    
    @FXML 
    void generateScenario(ActionEvent event) {
    	ArrayList<AbstractComponent> consumer, generator;
    	consumer = ConsumerFactory.generate(100, 100, 0);
    	generator = GeneratorFactory.generate(15, 11000, 5000);
    	//run the first iteration
    	for(AbstractComponent a : consumer) {			
    		control.addConsumer(a);
    	}
    	for(AbstractComponent a : generator) {			
    		control.addGenerator(a);
    	}	
    	showList();
    }
    void clearTextfield(TextField tf) {
    	tf.clear();
    }
    
    //get consumer list
    public ArrayList<AbstractComponent> getConsumerList(){
    	ArrayList<AbstractComponent> myList = new ArrayList<AbstractComponent>();
    	for(AbstractComponent i: control.getConsumers()) {
    		myList.add(i);
    	}
    	return myList;
    }
    //get generator list
    public ArrayList<AbstractComponent> getGeneratorList(){
    	ArrayList<AbstractComponent> myList = new ArrayList<AbstractComponent>();
    	for(AbstractComponent i: control.getGenerators()) {
    		myList.add(i);
    	}
    	return myList;
    }
    

}
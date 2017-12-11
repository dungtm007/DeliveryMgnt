package deliverymgnt.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import deliverymgnt.config.StageManager;
import deliverymgnt.domainclasses.Address;
import deliverymgnt.domainclasses.Customer;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Drone;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.services.CustomerService;
import deliverymgnt.services.DroneService;
import deliverymgnt.services.OrderService;
import deliverymgnt.views.FxmlView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

@Controller
public class ManageDroneController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private TableView<Drone> tableDrone;
    
    @FXML
    private TableColumn<Drone, String> colName;
    
    @FXML
    private TableColumn<Drone, Integer> colId;

    @FXML
    private TableColumn<Drone, String> colModel;

    @FXML
    private TableColumn<Drone, Boolean> colStatus;
    
    @FXML
    private TextField lblNewDroneName;

    @FXML
    private TextField lblNewDroneModel;
    
    @Autowired
	private DroneService droneService;
    
    private ObservableList<Drone> droneList = FXCollections.observableArrayList();
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    		loadToView();
		
		// Add data properties to table
		colId.setCellValueFactory(new PropertyValueFactory<Drone, Integer>("id"));
		colModel.setCellValueFactory(new PropertyValueFactory<Drone, String>("model"));
		colName.setCellValueFactory(new PropertyValueFactory<Drone, String>("droneId"));
		colStatus.setCellValueFactory(new PropertyValueFactory<Drone, Boolean>("available"));
		
		tableDrone.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		            tableDrone.getSelectionModel().getSelectedItem().getId();                   
		        }
		    }
		});
	}
    
    @FXML
    void back2DashBoard(ActionEvent event) {
    	try {
			stageManager.switchScene(FxmlView.MANAGER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

    @FXML
    void createDrone(ActionEvent event) {
    		String name = lblNewDroneName.getText();
    		String model = lblNewDroneModel.getText();
    		if(name != null &&  model != null) {
    			Drone a = new Drone(name, model, true);
    			if(droneService.save(a) != null) {
    				loadToView();
    			}
    		}
    }

    @FXML
    void deleteDrone(ActionEvent event) {
    		Drone a = tableDrone.getSelectionModel().getSelectedItem();
    		droneService.delete(a);
    		loadToView();
    }
    
    void loadToView() {
    		droneList.clear();
		droneList.addAll(droneService.findAll());
		tableDrone.setItems(droneList);
    }
}

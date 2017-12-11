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
import deliverymgnt.domainclasses.Locker;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.services.CustomerService;
import deliverymgnt.services.DroneService;
import deliverymgnt.services.LockerService;
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
public class ManageLockerController implements Initializable {

	@FXML
    private TextField lblNewLargeBox;

    @FXML
    private Button btnBack;

    @FXML
    private TextField lblNewMediumBox;

    @FXML
    private TableColumn<Locker, Integer> colSmallBoxes;

    @FXML
    private TableColumn<Locker, Integer> colLagreBoxes;

    @FXML
    private TextField lblSmallBox;

    @FXML
    private TableColumn<Locker, Integer> colMediumBoxes;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField lblNewAddress;

    @FXML
    private Button btnAdd;

    @FXML
    private TableColumn<Locker, Address> colAddress;

    @FXML
    private TableView<Locker> tableLocker;

    @FXML
    private TableColumn<Locker, Integer> colId;
    
    @Autowired
	private LockerService lockerService;
    
    private ObservableList<Locker> lockerList = FXCollections.observableArrayList();
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    		loadToView();
		
		// Add data properties to table
		colId.setCellValueFactory(new PropertyValueFactory<Locker, Integer>("id"));
		colAddress.setCellValueFactory(new PropertyValueFactory<Locker, Address>("address"));
		colLagreBoxes.setCellValueFactory(new PropertyValueFactory<Locker, Integer>("numOfLargeBox"));
		colMediumBoxes.setCellValueFactory(new PropertyValueFactory<Locker, Integer>("numOfMediumBox"));
		colSmallBoxes.setCellValueFactory(new PropertyValueFactory<Locker, Integer>("numOfSmallBox"));
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
    void createLocker(ActionEvent event) {
    		String add = lblNewAddress.getText();
    		String bigBox = lblNewLargeBox.getText();
    		String mediumBox = lblNewMediumBox.getText();
    		String smallBox = lblSmallBox.getText();
    		if(add != null 
    				&&  bigBox != null && validateNum(bigBox)
    				&& mediumBox != null && validateNum(mediumBox)
    				&& smallBox != null && validateNum(smallBox)) {
    			Locker a = new Locker(add, 
    					Integer.valueOf(bigBox), 
    					Integer.valueOf(mediumBox),
    					Integer.valueOf(smallBox));
    			if(lockerService.save(a) != null) {
    				loadToView();
    			}
    		}
    }

    @FXML
    void deleteLocker(ActionEvent event) {
    		Locker a = tableLocker.getSelectionModel().getSelectedItem();
    		lockerService.delete(a);
    		loadToView();
    }
    
    void loadToView() {
    		lockerList.clear();
		lockerList.addAll(lockerService.findAll());
		tableLocker.setItems(lockerList);
    }
    
    boolean validateNum(String text) {
    		return text.matches("[0-9]*");
    }
}

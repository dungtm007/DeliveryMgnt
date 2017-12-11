package deliverymgnt.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import deliverymgnt.domainclasses.Address;
import deliverymgnt.domainclasses.Customer;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.services.CustomerService;
import deliverymgnt.services.OrderService;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

@Controller
public class ManageOrderController implements Initializable {

	@FXML
    private TableView<Order> tableDetails;	

    @FXML
    private Label lblTest;
    
    @FXML
    private TableColumn<Order, DeliveryType> colDeliveryType;

    @FXML
    private TableColumn<Order, Address> colDeliveryAddress;

    @FXML
    private TableColumn<Order, Date> colOrderDate;

    @FXML
    private TableColumn<Order, Date> colDeliveryDeadline;

    @FXML
    private TableColumn<Order, OrderStatus> colOrderStatus;

    @FXML
    private TableColumn<Order, Double> colTotalPrice;

    @FXML
    private TableColumn<Order, Customer> colCustomer;

    @FXML
    private TableColumn<Order, Integer> colOrderId;
    
    @Autowired
	private OrderService orderService;
	
	@Autowired
	private CustomerService customerService;
	
    private List<Customer> listCustomer;
    private Customer selectedCustomer;
    private Order selectedOrder;
    
    private ObservableList<Order> orderList = FXCollections.observableArrayList();
	private ObservableList<OrderItem> orderItem = FXCollections.observableArrayList();
	private ObservableList<Customer> customerOb = FXCollections.observableArrayList();
	private ObservableList<Package> packageList = FXCollections.observableArrayList();
	private ObservableList<Integer> orderId = FXCollections.observableArrayList();
	
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    		orderList.clear();
    		orderList.addAll(orderService.findAll());
		tableDetails.setItems(orderList);
		
		// Add data properties to table
		colCustomer.setCellValueFactory(new PropertyValueFactory<Order,Customer>("customer"));
		colDeliveryAddress.setCellValueFactory(new PropertyValueFactory<Order,Address>("deliveryAddress"));
		colDeliveryDeadline.setCellValueFactory(new PropertyValueFactory<Order,Date>("deliveryDeadline"));
		colDeliveryType.setCellValueFactory(new PropertyValueFactory<Order,DeliveryType>("deliveryType"));
		colOrderDate.setCellValueFactory(new PropertyValueFactory<Order,Date>("orderDate"));
		colOrderId.setCellValueFactory(new PropertyValueFactory<Order,Integer>("id"));
		colOrderStatus.setCellValueFactory(new PropertyValueFactory<Order,OrderStatus>("orderStatus"));
		colTotalPrice.setCellValueFactory(new PropertyValueFactory<Order,Double>("totalPrice"));
		
		tableDetails.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		            lblTest.setText(String.valueOf(tableDetails.getSelectionModel().getSelectedItem().getId()));                   
		        }
		    }
		});
	}
    
    @FXML
    void clickOnTable(ActionEvent event) {

    }

    @FXML
    void editOrder(ActionEvent event) {

    }
}

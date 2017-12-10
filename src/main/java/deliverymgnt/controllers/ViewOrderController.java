package deliverymgnt.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import deliverymgnt.domainclasses.Customer;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.services.CustomerService;
import deliverymgnt.services.OrderService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

@Controller
public class ViewOrderController implements Initializable {
	public static final String GREETING = "Hi, ";
	@FXML 
	private ChoiceBox<Order> ddlOrder;
	
	@FXML
    private TableView<OrderItem> tableOrder;

    @FXML
    private Button btnSelect;

    @FXML
    private TableColumn<OrderItem, Integer> colQuality;

    @FXML
    private TableColumn<OrderItem, String> colProduct;
    
    @FXML
    private TableColumn<OrderItem, Integer> colPackage;

    @FXML
    private TableColumn<OrderItem, Double> colUnitPrice;
    
    @FXML
    private TableColumn<OrderItem, String> colStatus;

    @FXML
    private Label lblPlacedDate;

    @FXML
    private Label lblDestination;
    
    @FXML
    private Label lblGreeting;
    
    @FXML
    private Label lblOrderId;
    
    @FXML
    private Label lblArrivalDate;
    
	@FXML
	private Label lblTotalPrice;
    
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CustomerService customerService;
	
	private ObservableList<Order> orderList = FXCollections.observableArrayList();
	private ObservableList<OrderItem> orderItem = FXCollections.observableArrayList();
	private Customer customer;
	private Order order;

    @FXML
    void Package(ActionEvent event) {

    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Temporary get customer
		// TODO: Receive customer id via login screen
		customer = customerService.find(1);
		
		lblGreeting.setText(GREETING + customer.getFirstName());
		
		// Get order list by customer
		List<Order> listorder = orderService.findByCustomer(customer.getId());

		// Initialize choiceBox
		orderList.addAll(listorder);
		ddlOrder.setItems(orderList);
		
		// Bind ViewTable with orderItem
		tableOrder.setItems(orderItem);
		
		// Add data properties to table
		colProduct.setCellValueFactory(new PropertyValueFactory<OrderItem,String>("productName"));
		colQuality.setCellValueFactory(new PropertyValueFactory<OrderItem,Integer>("amount"));
		colStatus.setCellValueFactory(new PropertyValueFactory<OrderItem,String>("orderStatus"));
		colUnitPrice.setCellValueFactory(new PropertyValueFactory<OrderItem,Double>("unitPrice"));
	}
	
	@FXML
    private void selectOrder(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Select Product");
		alert.show();
	}
	
	@FXML
	private void updateOrder2Table(ActionEvent event) throws IOException {
		order = ddlOrder.getSelectionModel().getSelectedItem();
		if(!orderItem.isEmpty()) {
			orderItem.clear();
		}
		orderItem.addAll(order.getOrderItems());

		// Update test file
		lblDestination.setText(order.getDeliveryAddress().getAddress());
		lblPlacedDate.setText(order.getOrderDate().toString());
		lblTotalPrice.setText(String.valueOf(order.calculateTotalPrice()));
		lblOrderId.setText(String.valueOf(order.getId()));
		lblArrivalDate.setText(order.getDeliveryDeadline().toString());
	}

}

package deliverymgnt.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import deliverymgnt.config.StageManager;
import deliverymgnt.domainclasses.Address;
import deliverymgnt.domainclasses.Customer;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.services.CustomerService;
import deliverymgnt.services.OrderService;
import deliverymgnt.views.FxmlView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class OrdersListController implements Initializable {

	@FXML
    private TableView<Order> tableViewOrders;	

	@FXML
    private Button btnSelect;

    @FXML
    private Button btnDashboard;
    
    @FXML
    private Button btnViewDetails;
    
    @FXML
    private Label lblTitle;
    
    @FXML
    private TableColumn<Order, DeliveryType> colDeliveryType;

    @FXML
    private TableColumn<Order, Address> colDeliveryAddress;
    
    @FXML
    private TableColumn<Order, Address> colMethods;
    
    @FXML
    private TableColumn<Order, Address> colPackages;
    
    @FXML
    private TableColumn<Order, Address> colViewDetails;

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
	
	@Lazy
    @Autowired
    private StageManager stageManager;

	private ObservableList<Order> ordersList = FXCollections.observableArrayList();
	private Timer timer;
	
    @Override
	public void initialize(URL location, ResourceBundle resources) {

    	lblTitle.setStyle("-fx-text-fill: blue");
    	tableViewOrders.setStyle("-fx-font-size: 15");
    	
    	loadDataForTableView();
    	setColumnProperties();
    	setDefaultSorting();
    	setTimerToRefreshData();
	}
    
    private void setTimerToRefreshData() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// Refresh orders list
						ordersList.clear();
						ordersList.setAll(orderService.findAll());
						tableViewOrders.sort();
					}
				});
			}
		}, 0, 15000);
	}
    
    private void loadDataForTableView() {
    	ordersList.clear();
    	ordersList.setAll(orderService.findAll());
    	tableViewOrders.setItems(ordersList);
    }
    
	private void setColumnProperties(){
		
		colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderNo"));
		colOrderDate.setCellValueFactory(new PropertyValueFactory<>("OrderDateFormat"));
		colDeliveryDeadline.setCellValueFactory(new PropertyValueFactory<>("deliveryDeadlineFormat"));
		colDeliveryType.setCellValueFactory(new PropertyValueFactory<>("deliveryTypeShortDesc"));
		colDeliveryAddress.setCellValueFactory(new PropertyValueFactory<>("deliveryAddress"));
		colPackages.setCellValueFactory(new PropertyValueFactory<>("packagesShortSummary"));
		colMethods.setCellValueFactory(new PropertyValueFactory<>("deliveryMethods"));
		colOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
		
		colPackages.setStyle("-fx-text-fill: blue; -fx-font-weight:bold;");
		colMethods.setStyle("-fx-text-fill: #E58817; -fx-font-weight:bold;");
		colOrderStatus.setStyle("-fx-font-weight:bold;");
	}
    
	private void setDefaultSorting() {
		colOrderId.setSortType(TableColumn.SortType.DESCENDING);
		tableViewOrders.getSortOrder().add(colOrderId);
		tableViewOrders.sort();
	}
	
    @FXML
    void openViewOrderDetail(ActionEvent event) throws IOException {
		
    	Order order = tableViewOrders.getSelectionModel().getSelectedItem();
    	
    	if (order == null) {
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Please select an order!");
			alert.showAndWait();
			return ;
    	}
    	
    	// Cancel Timer
    	if (timer != null) {
    		timer.cancel();
    	}
    	ViewOrderDetailController controller = 
    			(ViewOrderDetailController) stageManager.switchScene(FxmlView.VIEW_ORDER_DETAILS);
    	controller.setOrder(order);
    }

    @FXML
    void backToDashboard(ActionEvent event) {
    	try {
    		
    		if (timer != null) {
        		timer.cancel();
        	}
    		
			stageManager.switchScene(FxmlView.MANAGER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    void showOrderDetail(Order order) {
		// TODO: Switch screen to view order detail with order id
    	try {
    		if (timer != null) {
        		timer.cancel();
        	}
			stageManager.switchScene(FxmlView.VIEW_ORDER_DETAILS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}

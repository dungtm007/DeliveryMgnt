package deliverymgnt.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import antlr.StringUtils;
import deliverymgnt.config.StageManager;
import deliverymgnt.domainclasses.Address;
import deliverymgnt.domainclasses.Customer;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Locker;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.domainclasses.Product;
import deliverymgnt.domainclasses.Warehouse;
import deliverymgnt.services.CustomerService;
import deliverymgnt.services.GeoComputingService;
import deliverymgnt.services.LockerService;
import deliverymgnt.services.OrderService;
import deliverymgnt.services.ProductService;
import deliverymgnt.services.impl.GeoComputingServiceImpl;
import deliverymgnt.views.FxmlView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@Controller
public class CreateOrderController implements Initializable {
	
	@FXML
	private ChoiceBox<Product> ddlProducts;
	
	@FXML
	private ChoiceBox<Locker> ddlLockers;
	
	@FXML
	private TextField txtAmount;
	
	@FXML
	private TextField txtUnitPrice;
	
	@FXML
	private Button btnAdd;
	
	@FXML
	private Label lblTotalPrice;
	
	@FXML
	private Label lblSelectedLocker;
	
	@FXML
	private TableView<OrderItem> tableOrderItems;
	
	@FXML
	private TableColumn<OrderItem, String> colProduct;
	
	@FXML
	private TableColumn<OrderItem, Integer> colAmount;
	
	@FXML
	private TableColumn<OrderItem, Double> colUnitPrice;
	
	@FXML
	private TableColumn<OrderItem, Double> colPrice;
	
	@FXML 
	private RadioButton rd2days;
	
	@FXML 
	private RadioButton rd5to7days;
	
	@FXML 
	private ToggleGroup toggleDeliveryDeadline;
	
	@FXML 
	private RadioButton rdHome;
	
	@FXML 
	private RadioButton rdLocker;
	
	@FXML 
	private ToggleGroup toggleDeliveryOption;
	
	@FXML
	private Button btnPlaceOrder;
	
	@FXML
	private TextField txtAddress;
	
	@FXML
	private TextField txtCity;
	
	@FXML
	private TextField txtState;
	
	@FXML
	private TextField txtZip;
	
	@FXML
	private Label lblShippingWeight;
	
	@FXML
	private Label lblOrderStatus;
	
	@FXML
	private CheckBox chkUseCustomerAddress;
	
	@FXML
	private Pane pnLocker;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private LockerService lockerService;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	private ObservableList<Product> productsList = FXCollections.observableArrayList();
	private ObservableList<OrderItem> orderItemsList = FXCollections.observableArrayList();
	private ObservableList<Locker> lockersList = FXCollections.observableArrayList();
	
	private Order order;
	private Customer customer;
	private Locker nearestLocker;
	
	@FXML
    private void selectProduct(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Select Product");
		alert.show();
	}
	
	@FXML
	private void addOrUpdateOrderItemToTable(ActionEvent event) throws IOException {

		if (order == null)
		{
			order = new Order();
			order.setCustomer(customer);
		}
		
		Product selProduct = ddlProducts.getSelectionModel().getSelectedItem();
		
		if (order.hasProduct(selProduct)) {
			
			OrderItem found = null;
			
			// find the order item in the list
			for(OrderItem oi : orderItemsList) {
				if (oi.getProduct().equals(selProduct)) {
					oi.setAmount(Integer.parseInt(txtAmount.getText()));
					oi.setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
					found = oi;
					break;
				}
			}
			
			if (found != null) {
				orderItemsList.remove(found);
				order.getOrderItems().remove(found);
			}
		}

		OrderItem oi = new OrderItem(selProduct, Integer.parseInt(txtAmount.getText()), Double.parseDouble(txtUnitPrice.getText()), order);
		orderItemsList.add(oi);	
		
		updateTotalPriceDisplay();
	}
	
	@FXML
	private void removeOrderItemFromTable(ActionEvent event) throws IOException {
		
		OrderItem orderItem = tableOrderItems.getSelectionModel().getSelectedItem();
		orderItemsList.remove(orderItem);
		order.getOrderItems().remove(orderItem);
		updateTotalPriceDisplay();
	}
	
	private void updateTotalPriceDisplay() {
		
		lblTotalPrice.setText(String.format("%.2f", order.calculateTotalPrice()));
		lblShippingWeight.setText(String.format("%.2f", order.calculateTotalShippingWeight()));
	}
	
	@FXML
	private void placeOrder(ActionEvent event) throws IOException {
		
		// Validate input before placing order
		if (!validateOrderBeforePlacement()) {
			return;
		}
		
		reviewOrderBeforePlacement();
		order = orderService.save(order);
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText("Your order is placed successfully!");
		alert.showAndWait();
		
		// Switch to Dashboard
		UserViewController controller = (UserViewController)stageManager.switchScene(FxmlView.CUSTOMER);
		controller.setCustomer(this.customer);
	}
	
	private boolean validateOrderBeforePlacement() {
		
		Alert alert = null;
		boolean hasWarning = false;
		String content = "";
		
		// Validate input before placing order
		if (order.getOrderItems().isEmpty()) {
			hasWarning = true;
			content = "Your order is empty!";
		}
		else if (!rd2days.isSelected() && !rd5to7days.isSelected()) {
			hasWarning = true;
			content = "When you want your order to be delivered (we offer early delivery with a small additional fee)?";
		}
		else if (!rdHome.isSelected() && !rdLocker.isSelected()) {
			hasWarning = true;
			content = "Please select your preferred way to get the packages (at home or pickup)!";
		}
		else if (txtAddress.getText().isEmpty() ||
				txtCity.getText().isEmpty() ||
				txtState.getText().isEmpty() ||
				txtZip.getText().isEmpty()) {
			hasWarning = true;
			content = "Please fill completed delivery address!";
		}
		
		if (hasWarning) {
			alert = new Alert(AlertType.WARNING);
			alert.setContentText(content);
			alert.showAndWait();
			return false;
		}
		
		return true;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		order = new Order();
		
		List<Product> products = productService.findAll();
		productsList.clear();
		productsList.addAll(products);
		ddlProducts.setItems(productsList);
		
		List<Locker> lockers = lockerService.findAll();
		lockersList.clear();
		lockersList.addAll(lockers);
		ddlLockers.setItems(lockersList);
		
		pnLocker.setVisible(false);
		
		ddlProducts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observable, Product product1, Product product2) {
            	
                if (product2 != null) {
//                	System.out.println("Previous product: " + product1);
//                    System.out.println("Selected product: " + product2);
                }
                
                txtUnitPrice.setText(String.format("%.2f", product2.getPrice()));
                txtAmount.setText("1");
                
            }
        });
		
		ddlLockers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Locker>() {
            @Override
            public void changed(ObservableValue<? extends Locker> observable, Locker locker1, Locker locker2) {
            	
                lblSelectedLocker.setText(locker2.toString());
                Address lockerAddr = locker2.getLockerAddress();
                txtAddress.setText(lockerAddr.getAddress());
                txtCity.setText(lockerAddr.getCity());
                txtState.setText(lockerAddr.getState());
                txtZip.setText(lockerAddr.getZip());
                
            }
        });
		
		orderItemsList.clear();
		tableOrderItems.setItems(orderItemsList);
		setColumnProperties();
	}
	
	private void setColumnProperties(){
		
		colProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
		colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
	}
	
	
	@FXML
	private void selectDeliveryOption (ActionEvent event) throws IOException {
		
		boolean isLockerDelivery = rdLocker.isSelected();
		pnLocker.setVisible(isLockerDelivery);
		txtAddress.setEditable(!isLockerDelivery);
		txtCity.setEditable(!isLockerDelivery);
		txtState.setEditable(!isLockerDelivery);
		txtZip.setEditable(!isLockerDelivery);
		chkUseCustomerAddress.setDisable(isLockerDelivery);
		
		if (isLockerDelivery) {
			if (nearestLocker == null) {
				// Search for nearest locker to customer address
				String customerAddress = customer.getAddress().toString();
				
				// Find the nearest warehouse with delivery address
				GeoComputingService googleMapSvc = new GeoComputingServiceImpl();
				double nearestDist = Double.MAX_VALUE;
				
				for(Locker locker : lockersList) {
					String lockerAddress = locker.getLockerAddress().toString();
					double dist = googleMapSvc.computeDistance(customerAddress, lockerAddress);
					
					System.out.println(">>>>>");
					System.out.println(" Distance from customer address (" + customerAddress  + ")");
					System.out.println(" to locker address (" + lockerAddress  + ") is:");
					System.out.println("    " + dist + " miles");
					System.out.println(">>>>>");
					
					if (dist < nearestDist) {
						nearestDist = dist;
						nearestLocker = locker;
					} 
				}
			}
			
			if (nearestLocker != null) {
				// set selected locker's address to address textboxes
				Address lockerAddr = nearestLocker.getLockerAddress();
				txtAddress.setText(lockerAddr.getAddress());
				txtCity.setText(lockerAddr.getCity());
				txtState.setText(lockerAddr.getState());
				txtZip.setText(lockerAddr.getZip());
			}
		}
	}
	
	@FXML
	private void selectDeliveryDeadline (ActionEvent event) throws IOException {
		
	}
	
	@FXML
	private void backToDashboard (ActionEvent event) throws IOException {
		
		UserViewController controller = (UserViewController)stageManager.switchScene(FxmlView.CUSTOMER);
		controller.setCustomer(this.customer);
	}
	
	@FXML
	private void useCustomerAddress (ActionEvent event) throws IOException {
		
		String addr = "";
		String city = "";
		String state = "";
		String zip = "";
		
		if(chkUseCustomerAddress.isSelected()) {
			Address addrress = customer.getAddress();
			addr = addrress.getAddress();
			city = addrress.getCity();
			state = addrress.getState();
			zip = addrress.getZip();
		}
		
		txtAddress.setText(addr);
		txtCity.setText(city);
		txtState.setText(state);
		txtZip.setText(zip);
	}
	
	@SuppressWarnings("deprecation")
	private void setDeliveryDeadlineForOrder() {
		int days = rd2days.isSelected() ? 2 : 7;
		
		// Simulations: no of days = no of hours
		Date od = order.getOrderDate();
		Date deliveryDate = new Date(od.getYear(), od.getMonth(), od.getDate(),
				od.getHours() + days, od.getMinutes(), od.getSeconds());
		order.setDeliveryDeadline(deliveryDate);
	}
	
	private void setDeliveryTypeForOrder() {
		DeliveryType type = rdHome.isSelected() ? DeliveryType.HomeDelivery : DeliveryType.LockerPickupDelivery;
		order.setDeliveryType(type);
	}
	
	private void setDeliveryAddressForOrder() {
		Address deliveryAddress = new Address(txtAddress.getText(), txtCity.getText(), txtState.getText(), txtZip.getText());
		order.setDeliveryAddress(deliveryAddress);
	}
	
	private void setLockerForOrderIfAny() {
		if (rdLocker.isSelected()) {
			Locker selectedLocker = ddlLockers.getSelectionModel().getSelectedItem();
			if (selectedLocker != null) {
				order.setLocker(selectedLocker);
			}
			else {
				order.setLocker(nearestLocker);
			}
		}
	}
	
	private void reviewOrderBeforePlacement() {
		order.setOrderDate(new Date());
		setDeliveryDeadlineForOrder();
		setDeliveryTypeForOrder();
		setDeliveryAddressForOrder();
		setLockerForOrderIfAny();
		order.setOrderStatus(OrderStatus.Entered);
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}

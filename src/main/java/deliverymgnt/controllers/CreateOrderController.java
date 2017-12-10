package deliverymgnt.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import antlr.StringUtils;
import deliverymgnt.domainclasses.Address;
import deliverymgnt.domainclasses.Customer;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.domainclasses.Product;
import deliverymgnt.services.CustomerService;
import deliverymgnt.services.OrderService;
import deliverymgnt.services.ProductService;
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
	
	@FXML // product
	private ChoiceBox<Product> ddlProducts;
	
	@FXML
	private TextField txtAmount;
	
	@FXML
	private TextField txtUnitPrice;
	
	@FXML
	private Button btnAdd;
	
	@FXML
	private Label lblTotalPrice;
	
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
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	private ObservableList<Product> productsList = FXCollections.observableArrayList();
	private ObservableList<OrderItem> orderItemsList = FXCollections.observableArrayList();
	
	private Order order;
	private Customer customer;
	
	private Timer timer;
	
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
				//orderItemsList.add(found);
			}
			
		}
		
		//else {
			OrderItem oi = new OrderItem(selProduct, Integer.parseInt(txtAmount.getText()), Double.parseDouble(txtUnitPrice.getText()), order);
			orderItemsList.add(oi);	
		//}
		
		
		updateTotalPriceDisplay();
	}
	
	private void updateTotalPriceDisplay() {
		
//		double totalPrice = 0.0;
//		for(OrderItem oi : orderItemsList) {
//			totalPrice += oi.calculatePrice();
//		}
		
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
		alert.show();
		
		btnPlaceOrder.setText("Update Order");
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
		
		List<Product> products = productService.findAll();
		productsList.addAll(products);
		ddlProducts.setItems(productsList);
		
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
		
		tableOrderItems.setItems(orderItemsList);
		setColumnProperties();
		
		// Temporary get customer
		customer = customerService.find(1);
		
//		Address addr = customer.getAddress();
//		txtAddress.setText(addr.getAddress());
//		txtCity.setText(addr.getCity());
//		txtState.setText(addr.getState());
//		txtZip.setText(addr.getZip());
		
		// Timer
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						if (order != null) {
							int id = order.getId();
							if (id > 0) {
								Order updatedOrder = orderService.find(id);
								lblOrderStatus.setText(updatedOrder.getOrderStatus().toString());
							}
						}
					}
				});
			}
		}, 0, 8000);
	}
	
	private void setColumnProperties(){
		
		colProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
		colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
	}
	
	@FXML
	private void selectDeliveryDeadline (ActionEvent event) throws IOException {
		
//		if (rd2days.isSelected()) {
//			order.setDeliveryDeadline(LocalDateTime.now().plusDays(2));
//		}
//		else if (rd5to7days.isSelected()) {
//			order.setDeliveryDeadline(LocalDateTime.now().plusDays(7));
//		}
//		
//		System.out.println("Delivery deadline: " + order.getDeliveryDeadline().toString());
	}
	
	@FXML
	private void selectDeliveryOption (ActionEvent event) throws IOException {
		
//		if (rdHome.isSelected()) {
//			order.setDeliveryOption(DeliveryOption.HomeDelivery);
//		}
//		else if (rd5to7days.isSelected()) {
//			order.setDeliveryOption(DeliveryOption.LockerPickupDelivery);
//		}
//		
//		System.out.println("Delivery option: " + order.getDeliveryOption().toString());
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
	
	private void reviewOrderBeforePlacement() {
		order.setOrderDate(new Date());
		setDeliveryDeadlineForOrder();
		setDeliveryTypeForOrder();
		setDeliveryAddressForOrder();
		order.setOrderStatus(OrderStatus.Entered);
	}
	
}

package deliverymgnt.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import antlr.StringUtils;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.domainclasses.Product;
import deliverymgnt.services.CustomerService;
import deliverymgnt.services.OrderService;
import deliverymgnt.services.ProductService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@Controller
public class CreateOrderController implements Initializable {
	
	@FXML // product
	private ChoiceBox<Product> ddlProducts;
	
	@FXML
	private ComboBox<Product> cbProducts;
	
	@FXML
	private TextField txtAmount;
	
	@FXML
	private TextField txtUnitPrice;
	
	@FXML
	private Button btnAdd;
	
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
	private RadioButton rdHome;
	
	@FXML 
	private RadioButton rdLocker;
	
	@FXML
	private Button btnPlaceOrder;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	private ObservableList<Product> productsList = FXCollections.observableArrayList();
	private ObservableList<OrderItem> orderItemsList = FXCollections.observableArrayList();
	
	
	@FXML
    private void selectProduct(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Select Product");
		alert.show();
	}
	
	@FXML
	private void addOrderItem(ActionEvent event) throws IOException {
//		Alert alert = new Alert(Alert.AlertType.INFORMATION);
//		alert.setContentText("Add order item");
//		alert.show();
		
		Product selProduct = ddlProducts.getSelectionModel().getSelectedItem();
		OrderItem oi = new OrderItem(selProduct, Integer.parseInt(txtAmount.getText()), Double.parseDouble(txtUnitPrice.getText()));
		orderItemsList.add(oi);
		
	}
	
	@FXML
	private void placeOrder(ActionEvent event) throws IOException {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText("Place order");
		alert.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		List<Product> products = productService.findAll();
		productsList.addAll(products);
		ddlProducts.setItems(productsList);
		cbProducts.setItems(productsList);
		cbProducts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observable, Product product1, Product product2) {
            	
                if (product2 != null) {
                	System.out.println("Previous product: " + product1);
                    System.out.println("Selected product: " + product2);
                }
                
            }
        });
		ddlProducts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observable, Product product1, Product product2) {
            	
                if (product2 != null) {
                	System.out.println("Previous product: " + product1);
                    System.out.println("Selected product: " + product2);
                }
                
                txtUnitPrice.setText(Double.toString(product2.getPrice()));
                txtAmount.setText("1");
                
            }
        });
		
		tableOrderItems.setItems(orderItemsList);
		setColumnProperties();
	}
	
	private void setColumnProperties(){
		
//		colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
//		colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//		colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
//		colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
//		colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
//		colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
//		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
//		colEdit.setCellFactory(cellFactory);
		
		//colProduct.setCellValueFactory(cellData -> cellData.getValue().getProduct().getName());
		colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
		
	}
	
}

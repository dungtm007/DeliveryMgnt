package deliverymgnt.controllers;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import deliverymgnt.config.StageManager;
import deliverymgnt.domainclasses.Customer;
import deliverymgnt.views.FxmlView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Controller
public class UserViewController implements Initializable {
    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnViewOrders;
    
    @FXML
    private ImageView imgLogo;
    
    @FXML
    private Label lblCustomerName;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    private Customer customer;

    @FXML
    void showCreateNewOrder(ActionEvent event) {
		try {
			CreateOrderController controller = 
					(CreateOrderController)stageManager.switchScene(FxmlView.CREATE_ORDER);
			controller.setCustomer(customer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void showViewOrders(ActionEvent event) {
     	try {
			stageManager.switchScene(FxmlView.VIEW_ORDER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String imagePath = "/pictures/hot_deals.jpg";
	    Image image = new Image(imagePath);
	    imgLogo.setImage(image);
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
		loadCustomerInfo();
	}
	
	private void loadCustomerInfo() {
		lblCustomerName.setText("Hello " + customer.getFirstName() + " " + customer.getLastName());
	}
}

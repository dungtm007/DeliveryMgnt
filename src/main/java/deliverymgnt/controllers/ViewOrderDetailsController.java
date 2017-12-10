package deliverymgnt.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import deliverymgnt.domainclasses.Order;
import deliverymgnt.services.OrderService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

@Controller
public class ViewOrderDetailsController implements Initializable {

	private Order order; // for data to display details
	
	private int orderId;
	
	@Autowired
	private OrderService orderService;
	
	@FXML
	private Label lblOrderNo;
	
	@FXML 
	private Label lblOrderStatus;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		order = orderService.find(this.orderId);
		
	}
	
	public void setParams(Object[] params) {
		//setOrderId
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
		this.order = orderService.find(this.orderId);
	}
	
	private void loadOrderIntoView() {

		lblOrderNo.setText(Integer.toString(this.order.getId()));
		
		lblOrderStatus.setText( this.order.getOrderStatus().toString());
		
	}
	
	
	
}

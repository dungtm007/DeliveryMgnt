package deliverymgnt.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import deliverymgnt.domainclasses.Package;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.services.OrderService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

@Controller
public class ViewOrderDetailController implements Initializable {

	private Order order; // for data to display details
	
	private int orderId;
	
	@Autowired
	private OrderService orderService;
	
	@FXML
	private Label lblOrderNo;
	
	@FXML 
	private Label lblOrderStatus;
	
	@FXML
	private VBox vBoxPkgContainer;
	
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
		loadOrderIntoView();
	}
	
	private void loadOrderIntoView() {

		lblOrderNo.setText(Integer.toString(this.order.getId()));
		
		lblOrderStatus.setText( this.order.getOrderStatus().toString());
		
		loadPackages();
	}
	
	private void loadPackages() {
		
		// vBoxPackage01
		// --hBoxPackageInfo01
		// ----lblPackageName01
		// ----lblPackageStatus01
		// ----lblPackageEA01
		// --vBoxPackageDelivery01
		// ----lblPackageAddress01
		// ----lblPackageDeliveryMethod01
		// --vBoxPackageProducts01
		// ----hBoxProduct0101
		// ------imageProduct0101
		// ------lblProductName0101
		
		// Get deliveries list
		Set<Delivery> deliveries = order.getDeliveries();
		
		// For every delivery, get packages list
		for(Delivery d : deliveries) {
			Set<Package> packages = d.getPackages();
			
			// For every package, get products list
			for (Package p : packages) {
				
				String packageId = Integer.toString(p.getId());
				
				// vBoxPackage01
				VBox vBoxPackage = new VBox();
				String vBoxPackageId = "vBoxPackage" + packageId;
				vBoxPackage.setId(vBoxPackageId);
				vBoxPkgContainer.getChildren().add(vBoxPackage);
								
				// --hBoxPackageInfo01
				HBox hBoxPackageInfo = new HBox();
				String hBoxPackageInfoId = "hBoxPackageInfo" + packageId;
				hBoxPackageInfo.setId(hBoxPackageInfoId);
				vBoxPackage.getChildren().add(hBoxPackageInfo);
				hBoxPackageInfo.setPrefHeight(100);
				// ----lblPackageName01
				Label lblPackageName = new Label();
				String lblPackageNameId = "lblPackageName" + packageId;
				lblPackageName.setId(lblPackageNameId);
				lblPackageName.setText("Package #" + packageId);
				hBoxPackageInfo.getChildren().add(lblPackageName);
				lblPackageName.setPadding(new Insets(15, 5, 5, 15));
				lblPackageName.setFont(Font.font("System", 30));
				lblPackageName.setPrefWidth(257);
				// ----lblPackageStatus01
				Label lblPackageStatus = new Label();
				String lblPackageStatusId = "lblPackageStatus" + packageId;
				lblPackageStatus.setId(lblPackageStatusId);
				lblPackageStatus.setText("Status:\n" + d.getDeliveryStatus().toString());
				hBoxPackageInfo.getChildren().add(lblPackageStatus);
				HBox.setMargin(lblPackageStatus, new Insets(0, 0, 0, 200));
				lblPackageStatus.setFont(Font.font("System", 22));
				lblPackageStatus.setPadding(new Insets(10, 5, 5, 15));
				lblPackageStatus.setPrefHeight(100);
				lblPackageStatus.setMinHeight(Control.USE_PREF_SIZE);
				lblPackageStatus.setMaxHeight(Control.USE_PREF_SIZE);
				lblPackageStatus.setPrefWidth(138);
				lblPackageStatus.setMinWidth(Control.USE_PREF_SIZE);
				lblPackageStatus.setMaxWidth(Control.USE_PREF_SIZE);
				lblPackageStatus.setTextAlignment(TextAlignment.CENTER);
				// ----lblPackageEA01
				Label lblPackageEA = new Label();
				String lblPackageEAId = "lblPackageEA" + packageId;
				lblPackageEA.setId(lblPackageEAId);
				lblPackageEA.setText(d.getEstimatedArrivalTime().toString());
				hBoxPackageInfo.getChildren().add(lblPackageEA);
				HBox.setMargin(lblPackageEA, new Insets(0, 0, 0, 80));
				lblPackageEA.setFont(Font.font("System", 22));
				lblPackageEA.setPadding(new Insets(10, 5, 5, 15));
				lblPackageEA.setPrefHeight(100);
				lblPackageEA.setMinHeight(Control.USE_PREF_SIZE);
				lblPackageEA.setMaxHeight(Control.USE_PREF_SIZE);
				lblPackageEA.setPrefWidth(277);
				lblPackageEA.setMinWidth(Control.USE_PREF_SIZE);
				lblPackageEA.setMaxWidth(Control.USE_PREF_SIZE);
				lblPackageEA.setTextAlignment(TextAlignment.CENTER);
				
				// --vBoxPackageDelivery01
				VBox vBoxPackageDelivery = new VBox();
				String vBoxPackageDeliveryId = "vBoxPackageDelivery" + packageId;
				vBoxPackageDelivery.setId(vBoxPackageDeliveryId);
				vBoxPackage.getChildren().add(vBoxPackageDelivery);
				// ----lblPackageAddress01
				Label lblPackageAddress = new Label();
				String lblPackageAddressId = "lblPackageAddress" + packageId;
				lblPackageAddress.setId(lblPackageAddressId);
				lblPackageAddress.setText(d.getDeliveryAddress().toString());
				vBoxPackageDelivery.getChildren().add(lblPackageAddress);
				lblPackageAddress.setFont(Font.font("System", 15));
				lblPackageAddress.setPadding(new Insets(2, 2, 2, 25));
				// ----lblPackageDeliveryMethod01
				Label lblPackageDeliveryMethod = new Label();
				String lblPackageDeliveryMethodId = "lblPackageDeliveryMethod" + packageId;
				lblPackageDeliveryMethod.setId(lblPackageDeliveryMethodId);
				lblPackageDeliveryMethod.setText(d.getDeliveryMethod().toString());
				vBoxPackageDelivery.getChildren().add(lblPackageDeliveryMethod);
				lblPackageDeliveryMethod.setFont(Font.font("System", 15));
				lblPackageDeliveryMethod.setPadding(new Insets(2, 2, 2, 25));
				
				// --vBoxPackageProducts01
				VBox vBoxPackageProducts = new VBox();
				String vBoxPackageProductsId = "vBoxPackageProducts" + packageId;
				vBoxPackageProducts.setId(vBoxPackageProductsId);
				vBoxPackage.getChildren().add(vBoxPackageProducts);
				
				Set<OrderItem> orderItems = p.getOrderItems();
				
				// For every product, create hBoxProductxxyy
				for(OrderItem oi : orderItems) {
					String productId = Integer.toString(oi.getProduct().getId());
					// ----hBoxProduct0101
					HBox hBoxProduct = new HBox();
					String hBoxProductId = "hBoxProduct" + packageId + productId;
					hBoxProduct.setId(hBoxProductId);
					vBoxPackageProducts.getChildren().add(hBoxProduct);
					// ------imageProduct0101
					String imageSource = "http://www.zappos.com/images/743/7433394/7769-677438-p.jpg";
					Image img = new Image(imageSource);
				    ImageView imageProduct = new ImageView(img);
				    String imageProductId = "imageProduct" + packageId + productId;
				    imageProduct.setId(imageProductId);
				    imageProduct.setFitWidth(120);
				    imageProduct.setFitHeight(120);
				    HBox.setMargin(imageProduct, new Insets(10, 0, 10, 60));
				    hBoxProduct.getChildren().add(imageProduct);
					// ------lblProductName0101
					Label lblProductName = new Label();
					String lblProductNameId = "lblProductName" + packageId + productId;
					lblProductName.setId(lblProductNameId);
					lblProductName.setText(oi.getProduct().getName());
					hBoxProduct.getChildren().add(lblProductName);
					lblProductName.setFont(Font.font("System", 15));
					HBox.setMargin(lblProductName, new Insets(10, 0, 10, 0));
					lblProductName.setPadding(new Insets(2, 2, 2, 25));
				}
			}
		}
		
	}
	
	
	
}

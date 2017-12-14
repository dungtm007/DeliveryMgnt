package deliverymgnt.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import deliverymgnt.domainclasses.Package;
import deliverymgnt.domainclasses.Warehouse;
import deliverymgnt.config.StageManager;
import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryMethod;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.services.OrderService;
import deliverymgnt.views.FxmlView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
	
	@FXML 
	private Button btnDashboard;
	
	@FXML 
	private Button btnOrdersList;
	
	@FXML
	private Label lblWarehouse;
	
	private Timer timer;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
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
	
	// Prepared order should be faster
	public void setOrder(Order order) {
		this.order = order;
		loadOrderIntoView();
	}
	
	private void loadOrderIntoView() {

		lblOrderNo.setText(Integer.toString(this.order.getId()));
		lblOrderStatus.setText("Status: " + this.order.getOrderStatus().toString());
		Warehouse wh = order.getWarehouse();
		String whName = wh != null ? wh.getName() : "";
		String whAddress = wh != null ? wh.getWarehouseAddress().toString() : "";
		lblWarehouse.setText("Collected from warehouse " + whName + " - " + whAddress);
		loadPackages();
		//setTimerToRefreshData();
	}
	
	private void setTimerToRefreshData() {
		// Timer
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// ...
						// 
						
					}
				});
			}
		}, 0, 6000);
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
				hBoxPackageInfo.setPrefWidth(1200);
				hBoxPackageInfo.minWidth(Control.USE_PREF_SIZE);
				hBoxPackageInfo.maxWidth(Control.USE_PREF_SIZE);
				
				// ----lblPackageName01
				Label lblPackageName = new Label();
				String lblPackageNameId = "lblPackageName" + packageId;
				lblPackageName.setId(lblPackageNameId);
				lblPackageName.setText("Package #" + packageId);
				hBoxPackageInfo.getChildren().add(lblPackageName);
				lblPackageName.setPadding(new Insets(15, 0, 5, 15));
				lblPackageName.setFont(Font.font("System", FontWeight.BOLD, 28));
				lblPackageName.setPrefWidth(230);
				lblPackageName.setPrefHeight(65);
				lblPackageName.setMinWidth(Control.USE_PREF_SIZE);
				
				// ----lblPackageStatus01
				Label lblPackageStatus = new Label();
				String lblPackageStatusId = "lblPackageStatus" + packageId;
				lblPackageStatus.setId(lblPackageStatusId);
				DeliveryStatus dStatus =  d.getDeliveryStatus();
				lblPackageStatus.setText("Status:\n" + dStatus.toString());
				if (dStatus == DeliveryStatus.Delivered ||
						dStatus == DeliveryStatus.Finished) {
					lblPackageStatus.setTextFill(Color.web("#258604"));
				} else if (dStatus == DeliveryStatus.Delivering) {
					lblPackageStatus.setTextFill(Color.web("#2f3ad6"));
				}
				else {
					lblPackageStatus.setTextFill(Color.web("#ea4314"));
				}
				hBoxPackageInfo.getChildren().add(lblPackageStatus);
				HBox.setMargin(lblPackageStatus, new Insets(0, 0, 0, 30));
				lblPackageStatus.setFont(Font.font("System", FontWeight.BOLD, 20));
				lblPackageStatus.setPadding(new Insets(10, 0, 5, 0));
				lblPackageStatus.setPrefHeight(100);
				lblPackageStatus.setMinHeight(Control.USE_PREF_SIZE);
				lblPackageStatus.setMaxHeight(Control.USE_PREF_SIZE);
				lblPackageStatus.setPrefWidth(115);
				lblPackageStatus.setMinWidth(Control.USE_PREF_SIZE);
				lblPackageStatus.setMaxWidth(Control.USE_PREF_SIZE);
				lblPackageStatus.setAlignment(Pos.TOP_CENTER);
				lblPackageStatus.setTextAlignment(TextAlignment.CENTER);
				
				// ----lblPackageShippingWeight01
				Label lblPackageShippingWeight = new Label();
				String lblPackageShippingWeightId = "lblPackageShippingWeight" + packageId;
				lblPackageShippingWeight.setId(lblPackageShippingWeightId);
				String shippingWeightStr = "Shipping Weight:\n";
				shippingWeightStr += String.format("%.2f", p.calculateShippingWeight()) + " lb(s)";
				lblPackageShippingWeight.setText(shippingWeightStr);
				
				lblPackageShippingWeight.setFont(Font.font("System", FontWeight.BOLD, 20));
				if (dStatus == DeliveryStatus.Delivered ||
						dStatus == DeliveryStatus.Finished) {
					lblPackageShippingWeight.setTextFill(Color.web("#258604"));
				} else if (dStatus == DeliveryStatus.Delivering) {
					lblPackageShippingWeight.setTextFill(Color.web("#2f3ad6"));
				}
				else {
					lblPackageShippingWeight.setTextFill(Color.web("#ea4314"));
				}
				HBox.setMargin(lblPackageShippingWeight, new Insets(0, 0, 0, 0));
				lblPackageShippingWeight.setPadding(new Insets(10, 5, 5, 5));
				lblPackageShippingWeight.setPrefHeight(100);
				lblPackageShippingWeight.setMinHeight(Control.USE_PREF_SIZE);
				lblPackageShippingWeight.setMaxHeight(Control.USE_PREF_SIZE);
				lblPackageShippingWeight.setPrefWidth(200);
				lblPackageShippingWeight.setMinWidth(Control.USE_PREF_SIZE);
				lblPackageShippingWeight.setMaxWidth(Control.USE_PREF_SIZE);
				lblPackageShippingWeight.setTextAlignment(TextAlignment.CENTER);
				lblPackageShippingWeight.setAlignment(Pos.TOP_CENTER);
				hBoxPackageInfo.getChildren().add(lblPackageShippingWeight);
				
								
				// ----lblPackageEA01
				Label lblPackageEA = new Label();
				String lblPackageEAId = "lblPackageEA" + packageId;
				lblPackageEA.setId(lblPackageEAId);
				String estimatedArrivalStr = "Estimated Arrival:\n";
				Date dea = d.getEstimatedArrivalTime();
				String deaFormat = dea != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dea) : "";
				lblPackageEA.setText(estimatedArrivalStr + deaFormat);
				lblPackageEA.setFont(Font.font("System", FontWeight.BOLD, 20));
				HBox.setMargin(lblPackageEA, new Insets(0, 0, 0, 0));
				if (dStatus == DeliveryStatus.Delivered ||
						dStatus == DeliveryStatus.Finished) {
					lblPackageEA.setTextFill(Color.web("#258604"));
				} else if (dStatus == DeliveryStatus.Delivering) {
					lblPackageEA.setTextFill(Color.web("#2f3ad6"));
				}
				else {
					lblPackageEA.setTextFill(Color.web("#ea4314"));
				}
				lblPackageEA.setPadding(new Insets(10, 5, 5, 5));
				lblPackageEA.setPrefHeight(100);
				lblPackageEA.setMinHeight(Control.USE_PREF_SIZE);
				lblPackageEA.setMaxHeight(Control.USE_PREF_SIZE);
				lblPackageEA.setPrefWidth(255);
				lblPackageEA.setMinWidth(Control.USE_PREF_SIZE);
				lblPackageEA.setMaxWidth(Control.USE_PREF_SIZE);
				lblPackageEA.setTextAlignment(TextAlignment.CENTER);
				lblPackageEA.setAlignment(Pos.TOP_CENTER);
				hBoxPackageInfo.getChildren().add(lblPackageEA);
				
				// ----lblPackageAA or RemainingDistance
				Label lblPackageAA = new Label();
				String lblPackageAAId = "lblPackageAA" + packageId;
				lblPackageAA.setId(lblPackageAAId);
				Date daa = d.getActualArrivalTime();
				if (daa != null) {
					String actualArrivalStr = "Actual Arrival:\n";
					String daaFormat = daa != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(daa) : "";
					lblPackageAA.setText(actualArrivalStr + daaFormat);
				}
				else {
					// switch to remaining distance
					String remainingDistStr = "Remaining distance:\n";
					remainingDistStr += d.getRemainingDistance() + " mile(s)";
					lblPackageAA.setText(remainingDistStr);
				}
				HBox.setMargin(lblPackageAA, new Insets(0, 0, 0, 0));
				lblPackageAA.setFont(Font.font("System", FontWeight.BOLD, 20));
				if (dStatus == DeliveryStatus.Delivered ||
						dStatus == DeliveryStatus.Finished) {
					lblPackageAA.setTextFill(Color.web("#258604"));
				} else if (dStatus == DeliveryStatus.Delivering) {
					lblPackageAA.setTextFill(Color.web("#2f3ad6"));
				}
				else {
					lblPackageAA.setTextFill(Color.web("#ea4314"));
				}
				lblPackageAA.setPadding(new Insets(10, 5, 5, 5));
				lblPackageAA.setPrefHeight(100);
				lblPackageAA.setMinHeight(Control.USE_PREF_SIZE);
				lblPackageAA.setMaxHeight(Control.USE_PREF_SIZE);
				lblPackageAA.setPrefWidth(255);
				lblPackageAA.setMinWidth(Control.USE_PREF_SIZE);
				lblPackageAA.setMaxWidth(Control.USE_PREF_SIZE);
				lblPackageAA.setTextAlignment(TextAlignment.CENTER);
				lblPackageAA.setAlignment(Pos.TOP_CENTER);
				hBoxPackageInfo.getChildren().add(lblPackageAA);
				
				// --vBoxPackageDelivery01
				VBox vBoxPackageDelivery = new VBox();
				String vBoxPackageDeliveryId = "vBoxPackageDelivery" + packageId;
				vBoxPackageDelivery.setId(vBoxPackageDeliveryId);
				vBoxPackage.getChildren().add(vBoxPackageDelivery);
				vBoxPackageDelivery.setPrefWidth(955);
				
				
				// ----lblPackageAddress01
				Label lblPackageAddress = new Label();
				String lblPackageAddressId = "lblPackageAddress" + packageId;
				lblPackageAddress.setId(lblPackageAddressId);
				
				// UPD
				if (d.getDeliveryType() == DeliveryType.HomeDelivery) {
					lblPackageAddress.setText("Package will be delivered to " + d.getDeliveryAddress().toString());
				}
				else {
					lblPackageAddress.setText("Package will be delivered to " + d.getLocker().toString());
				}
				lblPackageAddress.setFont(Font.font("System", FontWeight.BOLD, 19));
				lblPackageAddress.setTextFill(Color.web("#bc5e11"));
				lblPackageAddress.setPadding(new Insets(2, 2, 2, 25));
				lblPackageAddress.setPrefWidth(955);
//				lblPackageAddress.setMinWidth(Control.USE_PREF_SIZE);
//				lblPackageAddress.setMaxWidth(Control.USE_PREF_SIZE);
				vBoxPackageDelivery.getChildren().add(lblPackageAddress);
				// ----lblPackageDeliveryMethod01
				Label lblPackageDeliveryMethod = new Label();
				String lblPackageDeliveryMethodId = "lblPackageDeliveryMethod" + packageId;
				lblPackageDeliveryMethod.setId(lblPackageDeliveryMethodId);
				if (d.getDeliveryMethod() == DeliveryMethod.Courier) {
					lblPackageDeliveryMethod.setText("Delivery is handled by Courier Vendor");	
				}
				else {
					lblPackageDeliveryMethod.setText("Delivery is handled by our Drone Delivery(TM)");
				}
				lblPackageDeliveryMethod.setFont(Font.font("System", FontWeight.BOLD, 19));
				lblPackageDeliveryMethod.setTextFill(Color.web("#bc5e11"));
				lblPackageDeliveryMethod.setPadding(new Insets(2, 2, 2, 25));
				lblPackageDeliveryMethod.setPrefWidth(955);
//				lblPackageDeliveryMethod.setMinWidth(Control.USE_PREF_SIZE);
//				lblPackageDeliveryMethod.setMaxWidth(Control.USE_PREF_SIZE);
				vBoxPackageDelivery.getChildren().add(lblPackageDeliveryMethod);
				
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
					String imageSource = oi.getProduct().getProductImageSource();
					Image img = new Image(imageSource);
				    ImageView imageProduct = new ImageView(img);
				    String imageProductId = "imageProduct" + packageId + productId;
				    imageProduct.setId(imageProductId);
				    imageProduct.setFitWidth(150);
				    imageProduct.setFitHeight(150);
				    HBox.setMargin(imageProduct, new Insets(10, 0, 10, 60));
				    hBoxProduct.getChildren().add(imageProduct);
					// ------lblProductName0101
					Label lblProductName = new Label();
					String lblProductNameId = "lblProductName" + packageId + productId;
					lblProductName.setId(lblProductNameId);
					lblProductName.setText(oi.getProduct().getName());
					hBoxProduct.getChildren().add(lblProductName);
					lblProductName.setFont(Font.font("System", 17));
					HBox.setMargin(lblProductName, new Insets(10, 0, 10, 0));
					lblProductName.setPadding(new Insets(2, 2, 2, 25));
				}
				
				// Add separator
				Separator sep = new Separator();
				sep.setPrefWidth(750);
				vBoxPkgContainer.getChildren().add(sep);
			}
		}
		
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
	
	@FXML
    void backToOrdersList(ActionEvent event) {
    	try {
    		if (timer != null) {
        		timer.cancel();
        	}
			stageManager.switchScene(FxmlView.ORDERS_LIST_MNGR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
	
}

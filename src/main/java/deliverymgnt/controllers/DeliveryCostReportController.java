package deliverymgnt.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import deliverymgnt.config.StageManager;
import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryMethod;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.services.DeliveryService;
import deliverymgnt.services.OrderService;
import deliverymgnt.views.FxmlView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

@Controller
public class DeliveryCostReportController implements Initializable {

	private List<Delivery> deliveries;
	
	private ObservableList<Delivery> deliveriesList = FXCollections.observableArrayList();
	
	@Autowired
	private DeliveryService deliveryService;
	
	@FXML
	private RadioButton rdLast7Days;
	
	@FXML
	private RadioButton rdSpecificPeriod;
	
	@FXML
	private HBox boxFromTo;
	
	@FXML
	private DatePicker dpFrom;
	
	@FXML
	private DatePicker dpTo;
	
	@FXML
	private Label lblTotalCost;
	
	@FXML
	private Button btnDashboard;
	
	@FXML 
	private TableView<Delivery> tableViewDeliveries;
	
	@FXML
	private TableColumn<Delivery, String> colOrderNo;
	
	@FXML
	private TableColumn<Delivery, Date> colOrderDate;
	
	@FXML
	private TableColumn<Delivery, String> colDeliveryNo;
	
	@FXML
	private TableColumn<Delivery, DeliveryStatus> colStatus;
	
	@FXML
	private TableColumn<Delivery, Date> colShippedDate;
	
	@FXML
	private TableColumn<Delivery, Date> colArrivalDate;
	
	@FXML
	private TableColumn<Delivery, Double> colCost;
	
	@FXML
	private TableColumn<Delivery, String> colDistance;
	
	@FXML
	private TableColumn<Delivery, String> colMethod;
	
	@FXML
	private CheckBox chkDrone;
	
	@FXML
	private CheckBox chkCourier;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		deliveriesList.clear();
		tableViewDeliveries.setItems(deliveriesList);
		tableViewDeliveries.setStyle("-fx-font-size: 17");
		setColumnProperties();
		loadDeliveriesLast7Days(null);
	}
	
	@FXML
	private void selectDateFilter (ActionEvent event) throws IOException {
		boolean viewLast7Days = rdLast7Days.isSelected();
		boxFromTo.setDisable(viewLast7Days);
		
		if (viewLast7Days) {
			loadDeliveriesLast7Days(null);
		}
	}
	
	@FXML
	private void loadDeliveriesFromPeriod (ActionEvent event) throws IOException {
		
		DeliveryMethod method = null;
		if (!chkCourier.isSelected() && !chkDrone.isSelected()) {
			clearData();
			return;
		}
		if (chkCourier.isSelected() && !chkDrone.isSelected()) {
			method = DeliveryMethod.Courier;
		}
		else if (!chkCourier.isSelected() && chkDrone.isSelected()) {
			method = DeliveryMethod.Drone;
		}
		loadDeliveriesByDates(method);
	}
	
	@FXML
	private void backToDashboard(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.MANAGER);
	}
	
	@FXML
	private void filterByMethod (ActionEvent event) throws IOException {
		
		DeliveryMethod method = null;
		
		if (chkDrone.isSelected() && !chkCourier.isSelected()) {
			method = DeliveryMethod.Drone;
		}
		else if (!chkDrone.isSelected() && chkCourier.isSelected()) {
			method = DeliveryMethod.Courier;
		}
		
		if (method == null && !chkDrone.isSelected()) {
			clearData();
		}
		else {
			if (rdLast7Days.isSelected()) {
				loadDeliveriesLast7Days(method);
			}
			else {
				loadDeliveriesByDates(method);
			}
		}
	}
	
	private void clearData() {
		deliveriesList.clear();
		calculateAndDisplayTotalCost();
	}
	
	private void loadDeliveriesLast7Days(DeliveryMethod method) {
		
		Date today = new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		today.setDate(today.getDate() + 1);
		
		Date last7Days = new Date(today.getYear(), today.getMonth(), today.getDate(), 0, 0, 0);
		last7Days.setDate(today.getDate() - 6);
		
		if (method == null) {
			deliveries = deliveryService.findByStartTimeGreaterThanEqualAndStartTimeLessThanEqual(last7Days, today);
		}
		else {
			deliveries = deliveryService.findByStartTimeGreaterThanEqualAndStartTimeLessThanEqualAndDeliveryMethod(last7Days, today, method);
		}
		
		deliveriesList.setAll(deliveries);
		calculateAndDisplayTotalCost();
	}
	
	private void loadDeliveriesByDates(DeliveryMethod method) {
		LocalDate from = dpFrom.getValue();
		LocalDate to = dpTo.getValue();
	
		// Validate selected dates
		if (from == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Please select a From date!");
			alert.showAndWait();
			return;
		}
		
		if (to == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Please select a To date!");
			alert.showAndWait();
			return;
		}
		
		Date fromDate = new Date(from.getYear() - 1900, from.getMonthValue() - 1, from.getDayOfMonth());
		Date toDate = new Date(to.getYear() - 1900, to.getMonthValue() - 1, to.getDayOfMonth());
		toDate.setDate(toDate.getDate() + 1);
		
		System.out.println("<<<<<<<<<<<<<");
		System.out.println(fromDate);
		System.out.println(toDate);
		System.out.println("<<<<<<<<<<<<<");
		
		if (method == null) {
			deliveries = deliveryService.findByStartTimeGreaterThanEqualAndStartTimeLessThanEqual(fromDate, toDate);
		}
		else {
			deliveries = deliveryService.findByStartTimeGreaterThanEqualAndStartTimeLessThanEqualAndDeliveryMethod(fromDate, toDate, method);
		}
		
		deliveriesList.setAll(deliveries);
		calculateAndDisplayTotalCost();
	}
	
	private void setColumnProperties() {
		
		colOrderNo.setCellValueFactory(new PropertyValueFactory<>("orderNo"));
		colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDateFormat"));
		colDeliveryNo.setCellValueFactory(new PropertyValueFactory<>("deliveryNo"));
		colStatus.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));
		colShippedDate.setCellValueFactory(new PropertyValueFactory<>("startTimeFormat"));
		colArrivalDate.setCellValueFactory(new PropertyValueFactory<>("actualArrivalTimeFormat"));
		colCost.setCellValueFactory(new PropertyValueFactory<>("deliveryCostFormat"));
		
		colMethod.setCellValueFactory(new PropertyValueFactory<>("deliveryMethod"));
		colDistance.setCellValueFactory(new PropertyValueFactory<>("distanceFormat"));
		
		colDeliveryNo.setStyle("-fx-underline: true;");
		colOrderNo.setStyle("-fx-underline: true;");

		colDistance.setStyle("-fx-font-weight:bold;");
		colMethod.setStyle("-fx-font-weight:bold;");
		colCost.setStyle("-fx-font-weight:bold;");
	}
	
	private void calculateAndDisplayTotalCost() {
		double totalCost = 0.0;
		for(Delivery d : deliveries) {
			totalCost += d.getDeliveryCost(); 
		}
		
		lblTotalCost.setText("$ " + String.format("%.2f", totalCost));
	}
}

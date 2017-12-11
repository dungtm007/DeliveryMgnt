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
import deliverymgnt.views.FxmlView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

@Controller
public class UserViewController implements Initializable {
    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnViewOrders;
    
    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    void showPlacedOrder(ActionEvent event) {
    		try {
			stageManager.switchScene(FxmlView.CREATE_ORDER);
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
		
	}
}

package deliverymgnt.controllers;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import deliverymgnt.config.StageManager;
import deliverymgnt.views.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Controller
public class ManagerViewController implements Initializable {

    @FXML
    private Button btnManageOrder;

    @FXML
    private Button btnCostReport;

    @FXML
    private Button btnManageDrone;

    @FXML
    private Button btnManageLocker;
    
    @FXML
    private ImageView imgLogo;
    
    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    void showManageOrder(ActionEvent event) {
    		try {
			stageManager.switchScene(FxmlView.MANAGE_ORDER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void showManageLocker(ActionEvent event) {
    		try {
			stageManager.switchScene(FxmlView.MANAGE_LOCKER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

    @FXML
    void showManageDrone(ActionEvent event) {
    		try {
			stageManager.switchScene(FxmlView.MANAGE_DRONE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

    @FXML
    void showCostReport(ActionEvent event) {
    		try {
			stageManager.switchScene(FxmlView.DELIVERY_COST_REPORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String imagePath = "/pictures/delivery_management.png";
	    Image image = new Image(imagePath);
	    imgLogo.setImage(image);

	}
}

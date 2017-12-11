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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

@Controller
public class UserViewController implements Initializable {
	private List<String> menu = Arrays.asList("Create order", "Order list", "Log out");
    @FXML
    private MenuButton btnMenu;
    
    @Lazy
    @Autowired
    private StageManager stageManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		List<MenuItem> menuList = new ArrayList<MenuItem>();
		for(String i : menu) {
			menuList.add(new MenuItem(i));
			btnMenu.getItems().add(menuList.get(menu.indexOf(i)));
		}
		// Item 0
		menuList.get(0).setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    		try {
						stageManager.switchScene(FxmlView.CREATE_ORDER);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
		    }
		});
		
		// Item 1
		menuList.get(1).setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	try {
					stageManager.switchScene(FxmlView.VIEW_ORDER);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		    }
		});
		
		// Item 2
		menuList.get(2).setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    		try {
						stageManager.switchScene(FxmlView.LOGIN);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
		    }
		});
		
	}
}

package deliverymgnt.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import deliverymgnt.views.FxmlView;
import deliverymgnt.config.StageManager;
import deliverymgnt.domainclasses.Customer;
import deliverymgnt.domainclasses.UserType;
import deliverymgnt.services.CustomerService;
import deliverymgnt.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Controller
public class LoginController implements Initializable {

	@FXML
    private Button btnLogin;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label lblLogin;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private UserService userService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    @FXML
    private void login(ActionEvent event) throws IOException{
    		String user = getUsername();
    		String pass = getPassword();
    		if(user == null || pass == null) {
    			lblLogin.setText("Login Failed.");
    		} else {
    			
//    			OrdersListController controller = (OrdersListController)
//				stageManager.switchScene(FxmlView.MANAGER);

    			
    			UserType type = userService.authenticate(getUsername(), getPassword());
	    	    	if( type == UserType.CUSTOMER){    		    		
	    	   		stageManager.switchScene(FxmlView.CREATE_ORDER); 		
	    	    	}else if(type == UserType.MANAGER) {
	    	    		stageManager.switchScene(FxmlView.MANAGER); 
	    	    	}else{
	    	    		lblLogin.setText("Login Failed.");
	    	    	}
    		}
    		
    		
    	
    }
    
    public String getPassword() {
		return password.getText();
	}

	public String getUsername() {
		return username.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
}

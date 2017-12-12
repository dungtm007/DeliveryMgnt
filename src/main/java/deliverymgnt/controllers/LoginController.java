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
import deliverymgnt.domainclasses.User;
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
    		
    	String username = getUsername();
		String password = getPassword();
		
		if(username == null || password == null) {
			lblLogin.setText("Please enter username and password!");
		} 
		else {
			
			User user = userService.authenticate(getUsername(), getPassword());
			
	    	if(user.getUserType() == UserType.CUSTOMER) {    		    		
	    		
	    		UserViewController controller = (UserViewController)stageManager.switchScene(FxmlView.CUSTOMER);
	    		controller.setCustomer(user.getCustomer());
	    		
	    	} else if(user.getUserType() == UserType.MANAGER) {
	    		
	    		stageManager.switchScene(FxmlView.MANAGER);
	    		
	    	} else {
	    		
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

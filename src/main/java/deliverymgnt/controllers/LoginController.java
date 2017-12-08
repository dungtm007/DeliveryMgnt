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
import deliverymgnt.services.CustomerService;
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
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    @FXML
    private void login(ActionEvent event) throws IOException{
//    	if(userService.authenticate(getUsername(), getPassword())){
//    		    		
//   		stageManager.switchScene(FxmlView.USER);
//    		
//    	}else{
//    		lblLogin.setText("Login Failed.");
//    	}
    	
    	// switch to Trung's scene
    	//stageManager.switchScene(FxmlView.CREATE_ORDER);
    	
    	String result = "";
		
		for(Customer cust : customerService.findAll()){
			result += cust.toString() + "<br>";
		}
		
		//System.out.println("Hello " + (username != null ? username : "World") + "!");
		System.out.println(result);
    	
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText("123");
		alert.show();
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

import java.util.List;

import javafx.scene.Parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import deliverymgnt.domainclasses.Customer;
import deliverymgnt.repositories.CustomerRepository;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@SpringBootConfiguration
public class HelloWorld extends Application {
	
	private ConfigurableApplicationContext springContext;
	private Parent rootNode;
	
	@Autowired
	private CustomerRepository repository;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void init() {
		
		springContext = SpringApplication.run(HelloWorld.class);
		
		List<String> cmdlineParams = getParameters().getRaw();
		if(cmdlineParams.size() > 0) {
			username = cmdlineParams.get(0);
		}
		
	}
	
	String username;

	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Hello!");
		Button btn = new Button();
		btn.setText("Say 'Hello'");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				String result = "";
				
				for(Customer cust : repository.findAll()){
					result += cust.toString() + "<br>";
				}
				
				//System.out.println("Hello " + (username != null ? username : "World") + "!");
				System.out.println("Hello " + (username != null ? username : "World") + "!");
			}
		});
		StackPane root = new StackPane();
		root.getChildren().add(btn);
		
		//primaryStage.setScene(new Scene(root));
		Scene scene = new Scene(root, 300, 250);
		//scene.getStylesheets().add(getClass().getResource("hello.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	@Override
	public void stop() {
		//clean up resources
		springContext.close();
	}
}

package deliverymgnt.config;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import deliverymgnt.controllers.ViewOrderDetailsController;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

/**
 * Will load the FXML hierarchy as specified in the load method and register
 * Spring as the FXML Controller Factory. Allows Spring and Java FX to coexist
 * once the Spring Application context has been bootstrapped.
 */

@Component
public class SpringFXMLLoader {

	private final ResourceBundle resourceBundle;
	private final ApplicationContext context;
	
	@Autowired
	public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
		this.context = context;
		this.resourceBundle = resourceBundle;
	}
	
	// Object will contain Parent and Object (controller)
	public Object[] load(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory(context::getBean); // Spring now FXML Controller Factory
		loader.setResources(resourceBundle);
		loader.setLocation(getClass().getResource(fxmlPath));
		
		Parent view = loader.load();
		Object controller1 = loader.getController();
		
		//return loader.load();
		return new Object[] { view, controller1 };
	}
}

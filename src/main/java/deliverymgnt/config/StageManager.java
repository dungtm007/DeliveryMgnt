package deliverymgnt.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;

import deliverymgnt.controllers.ViewOrderDetailController;
import deliverymgnt.domainclasses.UserType;
import deliverymgnt.views.FxmlView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {
	
	private static final Logger LOG = getLogger(StageManager.class);
	private final Stage primaryStage;
	private final SpringFXMLLoader springFXMLLoader;
	private BorderPane rootLayout;
	private UserType userType;
	
	public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = stage;
	}
	
//	public void switchScene(final FxmlView view) {
//		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
//		show(viewRootNodeHierarchy, view.getTitle());
//	}
	
	public Object switchScene(final FxmlView view) throws IOException {
		
		Object[] viewAndController = loadViewNode(view.getFxmlFile());
		Parent viewNode = (Parent) viewAndController[0];
		
		if (view == FxmlView.LOGIN) {
			show(viewNode, view.getTitle());
		}
		else if(view == FxmlView.CREATE_ORDER || view == FxmlView.VIEW_ORDER) {
			userType = UserType.CUSTOMER;
			show(viewNode, view.getTitle());
		}
		else {
			userType = UserType.MANAGER;
			show(viewNode, view.getTitle());
		}
		return viewAndController[1];
	}
	
	private void show(final Parent rootNode, String title) {
		Scene scene = prepareScene(rootNode);
		
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();
		
		try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }
	}
	
	/*
	 * Change the main content of the root layout scene
	 * */
	private void showContent(final Parent contentNode, String title) throws IOException {
		
        if (rootLayout == null) {
            Object[] viewAndController = loadViewNode(FxmlView.ROOT.getFxmlFile());
            rootLayout = (BorderPane)viewAndController[0];
        }
        
		Scene scene = prepareScene(rootLayout);
		primaryStage.setScene(scene); // set scene is root layout
		
		// Load content
		AnchorPane content = (AnchorPane)contentNode;
		rootLayout.setCenter(content);
		
		primaryStage.setTitle(title);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();
		
		try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show content for title" + title,  exception);
        }
	}
	
	private Scene prepareScene(Parent rootNode) {
		Scene scene = primaryStage.getScene();
		
		if (scene == null) {
			scene = new Scene(rootNode);
		}
		scene.setRoot(rootNode);
		return scene;
	}
	
	/**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
	private Object[] loadViewNode(String fxmlFilePath) throws IOException {
		
		Parent node = null;
		
		//node = springFXMLLoader.load(fxmlFilePath);
		Object[] viewAndController = springFXMLLoader.load(fxmlFilePath);
		node = (Parent)viewAndController[0];
			
		return viewAndController;
	}
	
	private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }
}

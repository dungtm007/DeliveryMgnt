package deliverymgnt.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Objects;

import org.slf4j.Logger;

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
	
	public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = stage;
	}
	
//	public void switchScene(final FxmlView view) {
//		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
//		show(viewRootNodeHierarchy, view.getTitle());
//	}
	
	public void switchScene(final FxmlView view) {
		
		Parent viewNode = loadViewNodeHierarchy(view.getFxmlFile());
		
		if (view == FxmlView.LOGIN) {
			show(viewNode, view.getTitle());
		}
		else {
			showContent(viewNode, view.getTitle());
		}
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
	private void showContent(final Parent contentNode, String title) {
		
		// Load root layout (if not loaded)
		if (rootLayout == null) {
			rootLayout = (BorderPane)loadViewNodeHierarchy(FxmlView.ROOT_LAYOUT_VIEW.getFxmlFile());
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
	private Parent loadViewNodeHierarchy(String fxmlFilePath) {
		Parent rootNode = null;
		try {
			rootNode = springFXMLLoader.load(fxmlFilePath);
			Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
		}
		catch (Exception exception) {
			logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
		}
		return rootNode;
	}
	
	private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }
}

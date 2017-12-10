package deliverymgnt;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import deliverymgnt.config.StageManager;
import deliverymgnt.views.FxmlView;

@SpringBootApplication
@EnableScheduling
public class Main extends Application {
	
	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;
	
	public static void main(final String[] args) {
		Application.launch(args);
	}
	
	@Override 
	public void init() throws Exception {
		springContext = springBootApplicationContext();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stageManager = springContext.getBean(StageManager.class, stage);
		displayInitialScreen();
	}
	
	@Override
    public void stop() throws Exception {
        springContext.close();
    }
	
    /**
     * Useful to override this method by sub-classes wishing to change the first
     * Scene to be displayed on startup. Example: Functional tests on main
     * window.
     * @throws IOException 
     */
	protected void displayInitialScreen() throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
	}
	
	private ConfigurableApplicationContext springBootApplicationContext() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
        String[] args = getParameters().getRaw().stream().toArray(String[]::new);
        return builder.run(args);
    }
}


import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import view.MainWindow;
import view.Settings;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Main extends Application
{
	private final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
	private final double zoomSize = 10;

	@Override
	public void start(Stage primaryStage)
	{
		primaryStage.setTitle("Project Interface Graphic by HSIEH & FAN");
		PerspectiveCamera cam = new PerspectiveCamera();
		// cam.setFieldOfView(30);
		// cam.setFarClip(10000);
		// cam.setNearClip(0.01);
		// cam.getTransforms().addAll(new Rotate(60, Rotate.X_AXIS), new Translate(-200, -200, 300));

		MainWindow playground = new MainWindow();
		Scene scene = new Scene(playground);
		scene.getStylesheets().add(Settings.CSS_PATH);
		scene.setCamera(cam);
		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent evt)
			{
				if (evt.getCode().equals(KeyCode.ESCAPE))
				{
					playground.typedEsc();
				}
			}
		});

		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image(Settings.IMAGE_APP_ICON));
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
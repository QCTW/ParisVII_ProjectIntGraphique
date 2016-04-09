
import javafx.application.Application;
import javafx.stage.Stage;
import view.MainWindow;
import view.Settings;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		primaryStage.setTitle("Project Interface Graphic by HSIEH & FAN");
		PerspectiveCamera cam = new PerspectiveCamera();
		// cam.setFieldOfView(30);
		// cam.setFarClip(10000);
		// cam.setNearClip(0.01);
		// cam.getTransforms().addAll(new Rotate(60, Rotate.X_AXIS), new Translate(-200, -200, 300));

		Scene scene = new Scene(new MainWindow());
		scene.getStylesheets().add(Settings.CSS_PATH);
		scene.setCamera(cam);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image(Settings.IMAGE_APP_ICON));
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
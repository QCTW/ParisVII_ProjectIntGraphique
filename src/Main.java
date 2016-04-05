
import javafx.application.Application;
import javafx.stage.Stage;
import view.MainWindow;
import view.Settings;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		primaryStage.setTitle("Project Interface Graphic by HSIEH & FAN");
		Scene scene = new Scene(new MainWindow());
		scene.getStylesheets().add(Settings.CSS_PATH);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image(Settings.IMAGE_APP_ICON));
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
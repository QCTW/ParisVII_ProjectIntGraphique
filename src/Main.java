
import javafx.application.Application;
import javafx.stage.Stage;
import view.MainWindow;
import javafx.scene.Scene;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		primaryStage.setTitle("Project Interface Graphic by HSIEH & FAN");
		Scene scene = new Scene(new MainWindow());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
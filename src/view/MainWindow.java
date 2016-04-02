package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MainWindow extends BorderPane
{
	ToolBar toolBar = new ToolBar();
	StackPane contentArea = new StackPane();
	HBox controlBar = new HBox(5);

	public MainWindow()
	{
		super();
		initToolBar(toolBar);
		initContentArea(contentArea);
		initControlBar(controlBar);
		this.setTop(toolBar);
		this.setCenter(contentArea);
		this.setBottom(controlBar);
	}

	private void initContentArea(StackPane s)
	{
		s.setPrefSize(Settings.CONTENT_AREA_WIDTH, Settings.CONTENT_AREA_HEIGHT);
	}

	private void initControlBar(HBox h)
	{
		Ball b = new Ball();
		Cube c = new Cube();
		h.setPadding(new Insets(Settings.PADDING_IN_BAR));
		h.alignmentProperty().set(Pos.CENTER);
		h.getChildren().addAll(b, c);
	}

	private void initToolBar(ToolBar t)
	{

		Image imagebNew = new Image(getClass().getResourceAsStream(Settings.IMAGE_BNEW));
		Button bNew = new Button();// "New", new ImageView(imagebNew));
		bNew.setGraphic(new ImageView(imagebNew));
		t.getItems().add(bNew);

	}
}

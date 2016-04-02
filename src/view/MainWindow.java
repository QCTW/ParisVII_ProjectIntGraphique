package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainWindow extends BorderPane
{
	ToolBar toolBar = new ToolBar();
	HBox controlBar = new HBox(5);

	public MainWindow()
	{
		super();
		initToolBar(toolBar);
		initControlBar(controlBar);
		this.setTop(toolBar);
		this.setBottom(controlBar);
	}

	private void initControlBar(HBox h)
	{
		Ball b = new Ball();
		Cube c = new Cube();
		h.setPadding(new Insets(Settings.PADDING_IN_BAR));
		h.getChildren().addAll(b, c);
	}

	private void initToolBar(ToolBar t)
	{
		Button bNew = new Button("New");
		t.getItems().add(bNew);
	}
}

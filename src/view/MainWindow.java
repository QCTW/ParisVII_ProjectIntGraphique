package view;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainWindow extends BorderPane
{
	ToolBar toolBar = new ToolBar();
	HBox controlBar = new HBox();

	public MainWindow()
	{
		super();
		initToolBar(toolBar);
		initControlBar(controlBar);
		this.setTop(toolBar);
		this.setBottom(controlBar);
	}

	private void initControlBar(HBox controlBar2)
	{
		// TODO Auto-generated method stub

	}

	private void initToolBar(ToolBar t)
	{
		Button bNew = new Button("New");
		t.getItems().add(bNew);
	}
}

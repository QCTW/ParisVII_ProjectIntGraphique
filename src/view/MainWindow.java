package view;

import controller.EventHandlerMouseOverGlow;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class MainWindow extends BorderPane
{
	ToolBar toolBar = new ToolBar();
	StackPane contentArea = new StackPane();
	Label msgBox = new Label("Drag and drop a ball or cube to start your graph");

	public MainWindow()
	{
		super();
		initToolBar(toolBar);
		initContentArea(contentArea);
		initMessageBox(msgBox);
		this.setTop(toolBar);
		this.setCenter(contentArea);
		this.setBottom(msgBox);
	}

	private void initContentArea(StackPane s)
	{
		s.getStyleClass().add("stackpane");
		s.setPrefSize(Settings.CONTENT_AREA_WIDTH, Settings.CONTENT_AREA_HEIGHT);
	}

	private void initMessageBox(Label l)
	{

	}

	private void initToolBar(ToolBar t)
	{
		Icon iconNew = new Icon(Settings.IMAGE_BNEW);
		Button bNew = new Button();
		bNew.focusTraversableProperty().setValue(false);
		bNew.setGraphic(iconNew);
		bNew.setTooltip(new Tooltip("Start a new design graph"));

		Icon iconLoad = new Icon(Settings.IMAGE_BLOAD);
		Button bLoad = new Button();
		bLoad.focusTraversableProperty().setValue(false);
		bLoad.setGraphic(iconLoad);
		bLoad.setTooltip(new Tooltip("Load an existing graph"));

		Icon iconSave = new Icon(Settings.IMAGE_BSAVE);
		Button bSave = new Button();
		bSave.focusTraversableProperty().setValue(false);
		bSave.setTooltip(new Tooltip("Save current graph to a file"));
		bSave.setGraphic(iconSave);

		ControlButton bControlButton = new ControlButton();
		StepButton bStepButton = new StepButton();

		Ball b = new Ball();
		b.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandlerMouseOverGlow(b));
		Cube c = new Cube();
		t.setPadding(new Insets(Settings.PADDING_IN_BAR));
		// t.alignmentProperty().set(Pos.CENTER);
		// h.getChildren().addAll(b, c);

		t.getItems().addAll(bNew, bLoad, bSave, new Separator(), bControlButton, bStepButton, new Separator(), b, c);
	}
}

package view;

import controller.EventHandlerMouseOverGlow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
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
		b.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandlerMouseOverGlow(b));
		Cube c = new Cube();
		h.setPadding(new Insets(Settings.PADDING_IN_BAR));
		h.alignmentProperty().set(Pos.CENTER);
		h.getChildren().addAll(b, c);
	}

	private void initToolBar(ToolBar t)
	{
		Icon iconNew = new Icon(Settings.IMAGE_BNEW);
		Button bNew = new Button();
		bNew.setGraphic(iconNew);
		bNew.setTooltip(new Tooltip("Start a new design graph"));

		Icon iconLoad = new Icon(Settings.IMAGE_BLOAD);
		Button bLoad = new Button();
		bLoad.setGraphic(iconLoad);
		bLoad.setTooltip(new Tooltip("Load an existing graph"));

		Icon iconSave = new Icon(Settings.IMAGE_BSAVE);
		Button bSave = new Button();
		bSave.setTooltip(new Tooltip("Save current graph to a file"));
		bSave.setGraphic(iconSave);

		Separator sep = new Separator();

		ControlButton bControlButton = new ControlButton();
		StepButton bStepButton = new StepButton();

		t.getItems().addAll(bNew, bLoad, bSave, sep, bControlButton, bStepButton);

	}
}

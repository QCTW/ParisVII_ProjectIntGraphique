package view;

import java.io.File;
import java.util.Optional;
import java.util.Vector;

import controller.EventHandlerDragAndDrop;
import controller.EventHandlerGroupSelect;
import controller.EventHandlerMouseOverEnlarge;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.BaseNode;

public class MainWindow extends BorderPane
{
	ToolBar toolBar = new ToolBar();
	MainPane contentArea = new MainPane();
	Label msgBox = new Label("Drag and drop a ball or cube to start your graph");
	FileChooser fileChooser = new FileChooser();

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

	private void initContentArea(MainPane s)
	{
		s.getStyleClass().add("stackpane");
		s.setPrefSize(Settings.CONTENT_AREA_WIDTH, Settings.CONTENT_AREA_HEIGHT);
		EventHandlerDragAndDrop eventMaster = new EventHandlerDragAndDrop(s);
		s.setOnDragOver(eventMaster);
		s.setOnDragEntered(eventMaster);
		s.setOnDragExited(eventMaster);
		s.setOnDragDropped(eventMaster);
		s.setOnDragDone(eventMaster);

		EventHandlerGroupSelect eventGroupSelect = new EventHandlerGroupSelect(s);
		s.setOnMousePressed(eventGroupSelect);
		s.setOnMouseDragged(eventGroupSelect);
		s.setOnMouseReleased(eventGroupSelect);
	}

	private void initMessageBox(Label l)
	{

	}

	private void initToolBar(ToolBar t)
	{

		fileChooser.setTitle("File Navigator");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.serialized"));

		Icon iconNew = new Icon(Settings.IMAGE_BNEW);
		Button bNew = new Button();
		bNew.focusTraversableProperty().setValue(false);
		bNew.setGraphic(iconNew);
		bNew.setTooltip(new Tooltip("Start a new design graph"));
		bNew.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if ((contentArea.getAllNodes().size() != 0) || (contentArea.getAllConnection().size() != 0))
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Save Confirmation");
				alert.setHeaderText("You have not saved your current graph in the drawing board");
				alert.setContentText("Create a new graph without saving?");

				ButtonType buttonConfirm = new ButtonType("Yes");
				ButtonType buttonCancel = new ButtonType("No");

				alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonConfirm)
				{
					Vector<BaseNode> allNodes = contentArea.getAllNodes();
					for (int i = allNodes.size() - 1; i >= 0; i--)
					{
						allNodes.get(i).delete();
					}
				} else
				{
					File file = fileChooser.showSaveDialog(new Stage());
					System.out.println("Save file to " + file.getName());
				}
			}
		});

		Icon iconLoad = new Icon(Settings.IMAGE_BLOAD);
		Button bLoad = new Button();
		bLoad.focusTraversableProperty().setValue(false);
		bLoad.setGraphic(iconLoad);
		bLoad.setTooltip(new Tooltip("Load an existing graph"));
		bLoad.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			File file = fileChooser.showOpenDialog(new Stage());
			if (file != null)
			{
				System.out.println("Open file " + file.getName());
			} else
			{
				System.out.println("No file is chosen");
			}
		});

		Icon iconSave = new Icon(Settings.IMAGE_BSAVE);
		Button bSave = new Button();
		bSave.focusTraversableProperty().setValue(false);
		bSave.setTooltip(new Tooltip("Save current graph to a file"));
		bSave.setGraphic(iconSave);
		bSave.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			File file = fileChooser.showSaveDialog(new Stage());
			if (file != null)
			{
				System.out.println("Save to file " + file.getName());
			} else
			{
				System.out.println("No file is chosen");
			}
		});

		ControlButton bControlButton = new ControlButton();
		StepButton bStepButton = new StepButton();
		StopButton bStopButton = new StopButton();

		Ball b = new Ball(Settings.ICON_WIDTH_SIZE);
		b.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandlerMouseOverEnlarge(b));
		b.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandlerMouseOverEnlarge(b));
		Cube c = new Cube(Settings.ICON_WIDTH_SIZE);
		c.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandlerMouseOverEnlarge(c));
		c.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandlerMouseOverEnlarge(c));
		t.setPadding(new Insets(Settings.PADDING_IN_BAR));
		// t.alignmentProperty().set(Pos.CENTER);
		// h.getChildren().addAll(b, c);

		t.getItems().addAll(bNew, bLoad, bSave, new Separator(), bControlButton, bStopButton, bStepButton, new Separator(), b, c);
	}
}

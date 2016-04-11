package view;

import java.io.File;

import controller.EDragOperation;
import controller.EventHandlerDragAndDrop;
import controller.EventHandlerMouseOverEnlarge;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

	private void initContentArea(StackPane s)
	{
		s.getStyleClass().add("stackpane");
		s.setPrefSize(Settings.CONTENT_AREA_WIDTH, Settings.CONTENT_AREA_HEIGHT);
		contentArea.setOnDragOver(new EventHandlerDragAndDrop(s, EDragOperation.DragOver));
		contentArea.setOnDragEntered(new EventHandlerDragAndDrop(s, EDragOperation.DragEntered));
		contentArea.setOnDragExited(new EventHandlerDragAndDrop(s, EDragOperation.DragExited));
		contentArea.setOnDragDropped(new EventHandlerDragAndDrop(s, EDragOperation.DragDropped));
		contentArea.setOnDragDone(new EventHandlerDragAndDrop(s, EDragOperation.DragDone));
	}

	private void initMessageBox(Label l)
	{

	}

	private void initToolBar(ToolBar t)
	{
		
		fileChooser.setTitle("File Navigator");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.bmp", "*.png", "*.jpg", "*.gif"));

		
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
		bLoad.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
			File file = fileChooser.showOpenDialog(new Stage());
			if(file != null){
				System.out.println("open file "+file.getName());	
			}
			else{
				System.out.println("you don't choose any file");
			}
		});

		Icon iconSave = new Icon(Settings.IMAGE_BSAVE);
		Button bSave = new Button();
		bSave.focusTraversableProperty().setValue(false);
		bSave.setTooltip(new Tooltip("Save current graph to a file"));
		bSave.setGraphic(iconSave);
		bSave.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
			File file = fileChooser.showSaveDialog(new Stage());
			if(file != null){
				System.out.println("save file "+file.getName());
			}
			else{
				System.out.println("you don't choose any file");
			}
		});

		ControlButton bControlButton = new ControlButton();
		StepButton bStepButton = new StepButton();
		StopButton bStopButton = new StopButton();

		Ball b = new Ball();
		b.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandlerMouseOverEnlarge(b));
		b.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandlerMouseOverEnlarge(b));
		Cube c = new Cube();
		c.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandlerMouseOverEnlarge(c));
		c.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandlerMouseOverEnlarge(c));
		t.setPadding(new Insets(Settings.PADDING_IN_BAR));
		// t.alignmentProperty().set(Pos.CENTER);
		// h.getChildren().addAll(b, c);

		t.getItems().addAll(bNew, bLoad, bSave, new Separator(), bControlButton, bStopButton, bStepButton, new Separator(), b, c);
	}
}

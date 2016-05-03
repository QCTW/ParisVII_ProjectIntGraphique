package view;

import java.io.File;
import java.util.Optional;
import java.util.Vector;

import controller.EventHandlerDragAndDrop;
import controller.EventHandlerGroupSelect;
import controller.EventHandlerMouseOverEnlarge;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Serializor;

public class MainWindow extends BorderPane
{
	ToolBar toolBar = new ToolBar();
	MainPane contentArea = new MainPane();
	HBox descBox = new HBox();
	FileChooser fileChooser = new FileChooser();
	Serializor serializeur = new Serializor();

	public MainWindow()
	{
		super();
		initToolBar(toolBar);
		initContentArea(contentArea);
		initMessageBox(descBox);
		this.setTop(toolBar);
		this.setCenter(contentArea);
		this.setBottom(descBox);
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

	private void initMessageBox(HBox hb)
	{
		Ball bSrc = new Ball(Settings.ICON_WIDTH_SIZE);
		bSrc.getMaterial().setDiffuseColor(Settings.START_COLOR);
		Ball bDest = new Ball(Settings.ICON_WIDTH_SIZE);
		bDest.getMaterial().setDiffuseColor(Settings.END_COLOR);
		Ball bAlgoSrc = new Ball(Settings.ICON_WIDTH_SIZE);
		bAlgoSrc.getMaterial().setDiffuseColor(Settings.ALGO_SRC_COLOR);
		Ball bAlgoDest = new Ball(Settings.ICON_WIDTH_SIZE);
		bAlgoDest.getMaterial().setDiffuseColor(Settings.ALGO_DEST_COLOR);
		Ball bAlgoShortest = new Ball(Settings.ICON_WIDTH_SIZE);
		bAlgoShortest.getMaterial().setDiffuseColor(Settings.ALGO_SHORTEST_COLOR);
		Label labelSrc = new Label("SRC", bSrc.getFXNode());
		Label labelDest = new Label("DEST", bDest.getFXNode());
		Label labelAlgoSrc = new Label("Discovering SRC", bAlgoSrc.getFXNode());
		Label labelAlgoDest = new Label("Discovering DEST", bAlgoDest.getFXNode());
		Label labelAlgoShortest = new Label("Shortest", bAlgoShortest.getFXNode());
		hb.setSpacing(Settings.PADDING_IN_BAR);
		hb.setPadding(new Insets(Settings.PADDING_IN_BAR));
		hb.getChildren().addAll(labelSrc, labelDest, labelAlgoSrc, labelAlgoDest, labelAlgoShortest);
	}

	private void initToolBar(ToolBar t)
	{

		fileChooser.setTitle("File Navigator");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Graph serialized file (.ser)", "*.ser"));

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
					Vector<ViewableNode> allNodes = contentArea.getAllNodes();
					for (int i = allNodes.size() - 1; i >= 0; i--)
					{
						allNodes.get(i).delete();
					}
				} else
				{
					File file = fileChooser.showSaveDialog(new Stage());
					if (file != null)
					{
						serializeur.setFile(file);
						serializeur.serialize(contentArea);
						// msgBox.setText(serializeur.getMessage());
					} else
					{
						System.out.println("No file is chosen");
					}
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
				serializeur.setFile(file);
				MainPane mp = serializeur.deserialize();
				if (mp != null)
				{
					this.getChildren().remove(contentArea);
					contentArea = mp;
					initContentArea(contentArea);
					this.setCenter(contentArea);
					// msgBox.setText(serializeur.getMessage());
				} else
				{
					// msgBox.setText("Unable to open serialized file:" + file.getAbsolutePath());
				}
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
				serializeur.setFile(file);
				serializeur.serialize(contentArea);
				// msgBox.setText(serializeur.getMessage());
			} else
			{
				System.out.println("No file is chosen");
			}
		});

		ControlButton bControlButton = new ControlButton(contentArea);
		StepForwardButton bStepForwardButton = new StepForwardButton();
		StepBackButton bStepBackButton = new StepBackButton();
		StopButton bStopButton = new StopButton();

		Ball b = new Ball(Settings.ICON_WIDTH_SIZE);
		b.getFXNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandlerMouseOverEnlarge(b.getFXNode()));
		b.getFXNode().addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandlerMouseOverEnlarge(b.getFXNode()));
		Cube c = new Cube(Settings.ICON_WIDTH_SIZE);
		c.getFXNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandlerMouseOverEnlarge(c.getFXNode()));
		c.getFXNode().addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandlerMouseOverEnlarge(c.getFXNode()));
		t.setPadding(new Insets(Settings.PADDING_IN_BAR));
		// t.alignmentProperty().set(Pos.CENTER);
		// h.getChildren().addAll(b, c);

		CheckBox cbGraphReady = new CheckBox("Graph is ready");
		cbGraphReady.selectedProperty().addListener(new ChangeListener<Boolean>()
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> observalue, Boolean oldValue, Boolean newValue)
			{
				bControlButton.setDisable(!newValue);
				bStepForwardButton.setDisable(!newValue);
				bStepBackButton.setDisable(!newValue);
				bStopButton.setDisable(!newValue);
				if (newValue)
				{
					boolean condition1 = false;
					boolean condition2 = false;
					for (ViewableNode v : contentArea.getAllNodes())
					{
						if (condition1 == false && v.isStartNode() == true)
						{
							condition1 = true;
						}
						if (condition2 == false && v.isEndNode() == true)
						{
							condition2 = true;
						}
					}

					if (!(condition1 && condition2))
					{
						cbGraphReady.setSelected(false);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information");
						alert.setHeaderText(null);
						alert.setContentText("You have not set your start node or end node");
						alert.showAndWait();
						return;
					}

					for (ViewableNode v : contentArea.getAllNodes())
					{
						v.getEhm().setListener(false);
						v.getEhrc().setListener(false);
					}
					b.getEsd().setListener(false);
					c.getEsd().setListener(false);
				}

				else
				{
					for (ViewableNode v : contentArea.getAllNodes())
					{
						v.getEhm().setListener(true);
						v.getEhrc().setListener(true);
					}
					b.getEsd().setListener(true);
					c.getEsd().setListener(true);
				}

			}
		});

		t.getItems().addAll(bNew, bLoad, bSave, new Separator(), bControlButton, bStepBackButton, bStopButton, bStepForwardButton, new Separator(), cbGraphReady, new Separator(), b.getFXNode(),
				c.getFXNode());
	}
}

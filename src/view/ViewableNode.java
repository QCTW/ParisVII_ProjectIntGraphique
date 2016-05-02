package view;

import controller.EventHandlerMove;
import controller.EventHandlerRightClick;
import controller.EventHandlerSelectNode;
import controller.EventHandlerStartDrag;
import controller.LabelTextChangeListener;
import controller.VertexValueChangeListener;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import model.Edge;
import model.Noeud;

public abstract class ViewableNode extends Noeud
{
	public static String INFINITY_SYMBOL = "∞";
	private static final long serialVersionUID = 1L;
	private final MainPane contentArea;
	private final Group group = new Group();
	private boolean isSelectMode = false;
	private boolean isSelectedStartingNode = false;
	private double selectedTranslateX = 0;
	private double selectedTranslateY = 0;
	private final Label labelName = new Label();
	private final Label labelVertexValue = new Label();
	private final SelectedRing selectedRing = new SelectedRing();
	private final PhongMaterial material = new PhongMaterial();

	public ViewableNode(double size, MainPane mp)
	{
		super();
		contentArea = mp;
		initGraphicSetting(size);
		if (size == Settings.ICON_WIDTH_SIZE)
			group.setOnDragDetected(new EventHandlerStartDrag(this));
		else
		{
			EventHandlerMove ehm = new EventHandlerMove(this, mp);
			group.setOnMousePressed(ehm);
			group.setOnMouseDragged(ehm);
			group.setOnMouseReleased(ehm);
			group.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandlerRightClick(this));

			EventHandlerSelectNode ehsn = new EventHandlerSelectNode(this, mp);
			group.addEventHandler(MouseEvent.MOUSE_CLICKED, ehsn);
			group.addEventHandler(MouseEvent.MOUSE_ENTERED, ehsn);
			group.addEventHandler(MouseEvent.MOUSE_EXITED, ehsn);
		}
	}

	public void initGraphicSetting(double size)
	{
		if (size != Settings.ICON_WIDTH_SIZE)
		{
			labelName.setText(getNodeLabel());
			labelName.widthProperty().addListener(new LabelTextChangeListener(labelName));
			labelVertexValue.setText(INFINITY_SYMBOL);
			labelVertexValue.widthProperty().addListener(new VertexValueChangeListener(labelVertexValue));
			selectedRing.initGraphicSetting(size);
			group.getChildren().add(labelName);
			group.getChildren().add(labelVertexValue);
		}
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Settings.SPECULAR_COLOR);
	}

	public void moveTo(double deltaX, double deltaY, double deltaZ)
	{
		group.setTranslateX(selectedTranslateX + deltaX);
		group.setTranslateY(selectedTranslateY + deltaY);
		setPosX(group.getTranslateX());
		setPosY(group.getTranslateY());
		setPosZ(deltaZ);
		// System.out.println("Ball moveTo(" + deltaX + "," + deltaY + "," + deltaZ + ") Layout(" + this.getLayoutX() + "," + this.getLayoutY() + ") Translate(" + this.getTranslateX() + "," +
		// this.getTranslateY());
	}

	public Group getFXNode()
	{
		return group;
	}

	public void displaySelected()
	{
		selectedTranslateX = group.getTranslateX();
		selectedTranslateY = group.getTranslateY();
		group.getChildren().add(0, selectedRing);
		ensureAllConnectionsUnderNodes();
	}

	public void removeSelected()
	{
		group.getChildren().remove(selectedRing);
		ensureAllConnectionsUnderNodes();
	}

	private void ensureAllConnectionsUnderNodes()
	{
		for (Edge one : this.getEdges())
		{
			((ViewableEdge) one).getFXNode().toBack();
		}
	}

	public boolean isSelectMode()
	{
		return isSelectMode;
	}

	public boolean isSelectedStartingNode()
	{
		return isSelectedStartingNode;
	}

	public void resetSelectedStartingNode()
	{
		isSelectedStartingNode = false;
	}

	public Point3D getPoint3D()
	{
		return group.localToParent(group.getLayoutX(), group.getLayoutY(), 0);
	}

	public void delete()
	{
		contentArea.deleteNode(this);
	}

	public void setAction(ActionType atype)
	{
		contentArea.setAction(atype);
	}

	public void setDisabled()
	{
		group.setEffect(Settings.DISABLE_EFFECT);
	}

	public void setEnabled()
	{
		group.setEffect(null);
	}

	public void removeSelectedGroup()
	{
		contentArea.removeSelectedGroup();
	}

	public boolean hasNodeToConnect()
	{
		int nTotalNodes = contentArea.getAllNodes().size();
		if ((nTotalNodes - 1) > this.getEdges().size())
			return true;

		return false;
	}

	@Override
	public void setNodeLabel(String sLabel)
	{
		super.setNodeLabel(sLabel);
		labelName.setText(getNodeLabel());
	}

	@Override
	public void setStartNode(boolean isOrNot)
	{
		super.setStartNode(isOrNot);
		if (isOrNot)
		{
			material.setDiffuseColor(Settings.START_COLOR);
			contentArea.resetOtherStartNodes(this);
		} else
		{
			this.setVertexValue(INFINITY);
			if (!isEndNode())
				material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		}
		contentArea.updateEdgesDisplay(); // To ensure that all edges are at the back layer
	}

	@Override
	public void setEndNode(boolean isOrNot)
	{
		super.setEndNode(isOrNot);
		if (isOrNot)
		{
			material.setDiffuseColor(Settings.END_COLOR);
			contentArea.resetOtherEndNodes(this);
		} else
		{
			if (!isStartNode())
				material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		}
		contentArea.updateEdgesDisplay(); // To ensure that all edges are at the back layer
	}

	/**
	 * This method is used to start/end a selection process and is different from setSelectMode
	 * Implements in View
	 * 
	 * @param onOrOff
	 */
	public void selectMode(boolean onOrOff)
	{
		isSelectMode = onOrOff;
		if (onOrOff)
		{
			contentArea.startNodeSelectMode(this);
			isSelectedStartingNode = true;
		} else
		{
			contentArea.stopNodeSelectMode(this);
		}
	}

	/**
	 * This method is used to set this node to select mode so that it will react to the mouse event
	 * 
	 * @param trueOrFalse
	 */
	public void setSelectMode(boolean trueOrFalse)
	{
		isSelectMode = trueOrFalse;
	}

	public Material getMaterial()
	{
		return material;
	}

}

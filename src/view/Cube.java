package view;

import java.util.Vector;

import controller.EventHandlerMove;
import controller.EventHandlerRightClick;
import controller.EventHandlerSelectNode;
import controller.EventHandlerStartDrag;
import controller.LabelTextChangeListener;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import model.BaseNode;

public class Cube extends Group implements BaseNode
{
	private static final long serialVersionUID = 1L;
	private int nodeId = 0;
	private long vertexValue = 0;
	private double posX = 0; // Always = layoutX
	private double posY = 0; // Always = layoutY
	private double posZ = 0; // Always = layoutZ
	private double selectedTranslateX = 0;
	private double selectedTranslateY = 0;
	private boolean isSelectMode = false;
	private boolean isSelectedStartingNode = false;
	private boolean isStartNode = false;
	private boolean isEndNode = false;
	private String nodeLabel = "Cube";
	private MainPane contentArea = null;
	private final Box box;
	private final Label label;
	private final SelectedRing selectedRing;
	private final Vector<Connection> vConnectedNodes;
	private PhongMaterial material;

	public Cube(double size)
	{
		this(size, null);
	}

	public Cube(double size, MainPane mp)
	{
		vConnectedNodes = new Vector<Connection>();
		contentArea = mp;
		box = new Box(size, size, size);
		label = new Label();
		selectedRing = new SelectedRing(size);
		initGraphicSetting(size);
		if (size == Settings.ICON_WIDTH_SIZE) // If it is icon
			this.setOnDragDetected(new EventHandlerStartDrag(this));
		else // If this is a node in the graph
		{
			EventHandlerMove ehm = new EventHandlerMove(this, mp);
			this.setOnMousePressed(ehm);
			this.setOnMouseDragged(ehm);
			this.setOnMouseReleased(ehm);
			this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandlerRightClick(this));

			EventHandlerSelectNode ehsn = new EventHandlerSelectNode(this, mp);
			this.addEventHandler(MouseEvent.MOUSE_CLICKED, ehsn);
			this.addEventHandler(MouseEvent.MOUSE_ENTERED, ehsn);
			this.addEventHandler(MouseEvent.MOUSE_EXITED, ehsn);
		}

		this.getChildren().add(box);
		this.getChildren().add(label);
	}

	private void initGraphicSetting(double size)
	{
		if (size != Settings.ICON_WIDTH_SIZE)
		{
			nodeId = Utility.generateId();
			nodeLabel = "Cube-" + nodeId;
			label.setText(nodeLabel);
			label.widthProperty().addListener(new LabelTextChangeListener(label));
		}
		material = new PhongMaterial();
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Settings.SPECULAR_COLOR);
		box.setMaterial(material);
		// this.getTransforms().add(new Rotate(30, 0, 0, 0, Rotate.X_AXIS));
		// this.getTransforms().add(new Rotate(30, 0, 0, 0, Rotate.Y_AXIS));
		// this.setCullFace(CullFace.NONE);
		// this.setDrawMode(DrawMode.LINE);
	}

	@Override
	public int getNodeId()
	{
		return this.nodeId;
	}

	@Override
	public void moveTo(double deltaX, double deltaY, double deltaZ)
	{
		this.setTranslateX(selectedTranslateX + deltaX);
		this.setTranslateY(selectedTranslateY + deltaY);
		posX = this.getTranslateX();
		posY = this.getTranslateY();
		posZ = deltaZ;
		// System.out.println("Cube moveTo(" + deltaX + "," + deltaY + "," + deltaZ + ") Layout(" + this.getLayoutX() + "," + this.getLayoutY() + ") Translate(" + this.getTranslateX() + "," +
		// this.getTranslateY());
	}

	@Override
	public String getNodeLabel()
	{
		return nodeLabel;
	}

	@Override
	public void setNodeLabel(String sLabel)
	{
		nodeLabel = sLabel;
		label.setText(nodeLabel);
	}

	@Override
	public double getPosX()
	{
		return posX;
	}

	@Override
	public double getPosY()
	{
		return posY;
	}

	@Override
	public double getPosZ()
	{
		return posZ;
	}

	@Override
	public Node getFXNode()
	{
		return this;
	}

	@Override
	public void displaySelected()
	{
		selectedTranslateX = this.getTranslateX();
		selectedTranslateY = this.getTranslateY();
		this.getChildren().add(selectedRing);
		selectedRing.toBack();
	}

	@Override
	public void removeSelected()
	{
		this.getChildren().remove(selectedRing);
	}

	@Override
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

	@Override
	public boolean isSelectMode()
	{
		return isSelectMode;
	}

	@Override
	public boolean isSelectedStartingNode()
	{
		return isSelectedStartingNode;
	}

	@Override
	public void resetSelectedStartingNode()
	{
		isSelectedStartingNode = false;
	}

	@Override
	public void setSelectMode(boolean trueOrFalse)
	{
		isSelectMode = trueOrFalse;
	}

	@Override
	public Point3D getPoint3D()
	{
		return this.localToParent(this.getFXNode().getLayoutX(), this.getFXNode().getLayoutY(), 0);
	}

	@Override
	public void addEdge(Connection conn)
	{
		vConnectedNodes.add(conn);
	}

	@Override
	public void removeEdge(Connection conn)
	{
		vConnectedNodes.remove(conn);
	}

	@Override
	public Vector<Connection> getEdges()
	{
		return vConnectedNodes;
	}

	@Override
	public void delete()
	{
		contentArea.deleteNode(this);
	}

	@Override
	public void setAction(ActionType atype)
	{
		contentArea.setAction(atype);
	}

	@Override
	public void setDisabled()
	{
		this.setEffect(Settings.DISABLE_EFFECT);
	}

	@Override
	public void setEnabled()
	{
		this.setEffect(null);
	}

	@Override
	public boolean isStartNode()
	{
		return isStartNode;
	}

	@Override
	public boolean isEndNode()
	{
		return isEndNode;
	}

	@Override
	public void setStartNode(boolean isOrNot)
	{
		if (isOrNot)
		{
			this.setVertexValue(0);
			material.setDiffuseColor(Settings.START_COLOR);
			contentArea.resetOtherStartNodes(this);
		} else
		{
			this.setVertexValue(BaseNode.INFINITY);
			if (!isEndNode)
				material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		}
		contentArea.updateEdgesDisplay(); // To ensure that all edges are at the back layer
		isStartNode = isOrNot;
	}

	@Override
	public void setEndNode(boolean isOrNot)
	{
		if (isOrNot)
		{
			material.setDiffuseColor(Settings.END_COLOR);
			contentArea.resetOtherEndNodes(this);
		} else
		{
			if (!isStartNode)
				material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		}
		contentArea.updateEdgesDisplay(); // To ensure that all edges are at the back layer
		isEndNode = isOrNot;
	}

	@Override
	public boolean hasNodeToConnect()
	{
		int nTotalNodes = contentArea.getAllNodes().size();
		if ((nTotalNodes - 1) > vConnectedNodes.size())
			return true;

		return false;
	}

	@Override
	public boolean hasEdgeToDisconnect()
	{
		if (vConnectedNodes.size() > 0)
			return true;

		return false;
	}

	@Override
	public void removeSelectedGroup()
	{
		contentArea.removeSelectedGroup();
	}

	@Override
	public long getVertexValue()
	{
		return vertexValue;
	}

	@Override
	public void setVertexValue(long newValue)
	{
		vertexValue = newValue;
	}

}

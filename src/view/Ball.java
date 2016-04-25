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
import javafx.scene.shape.Sphere;
import model.BaseNode;

public class Ball extends Group implements BaseNode
{
	private static final long serialVersionUID = 1L;
	private int nodeId = 0;
	private double posX = 0;
	private double posY = 0;
	private double posZ = 0;
	private boolean isSelectMode = false;
	private boolean isSelectedStartingNode = false;
	private boolean isStartNode = false;
	private boolean isEndNode = false;
	private String nodeLabel;
	private final MainPane contentArea;
	private final Sphere sphere;
	private final Label label;
	private final SelectedRing selectedRing;
	private final Vector<Connection> vConnectedNodes;
	private PhongMaterial material;

	public Ball(double size)
	{
		this(size, null);
	}

	public Ball(double size, MainPane mp)
	{
		vConnectedNodes = new Vector<Connection>();
		contentArea = mp;
		sphere = new Sphere(size / 2);
		this.getChildren().add(sphere);
		label = new Label();
		this.getChildren().add(label);
		selectedRing = new SelectedRing(size);
		initGraphicSetting(size);
		if (size == Settings.ICON_WIDTH_SIZE)
			this.setOnDragDetected(new EventHandlerStartDrag(this));
		else
		{
			EventHandlerMove ehm = new EventHandlerMove(this);
			this.setOnMousePressed(ehm);
			this.setOnMouseDragged(ehm);
			this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandlerRightClick(this));

			EventHandlerSelectNode ehsn = new EventHandlerSelectNode(this, mp);
			this.addEventHandler(MouseEvent.MOUSE_CLICKED, ehsn);
			this.addEventHandler(MouseEvent.MOUSE_ENTERED, ehsn);
			this.addEventHandler(MouseEvent.MOUSE_EXITED, ehsn);
		}
	}

	private void initGraphicSetting(double size)
	{
		if (size != Settings.ICON_WIDTH_SIZE)
		{
			nodeId = Utility.generateId();
			nodeLabel = "Ball-" + nodeId;
			label.setText(nodeLabel);
			label.widthProperty().addListener(new LabelTextChangeListener(label));
		}
		material = new PhongMaterial();
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Settings.SPECULAR_COLOR);
		sphere.setMaterial(material);
		// this.setCullFace(CullFace.BACK);
		// this.setDrawMode(DrawMode.LINE);
	}

	@Override
	public int getNodeId()
	{
		return this.nodeId;
	}

	@Override
	public void moveTo(double x, double y, double z)
	{
		this.setTranslateX(x);
		this.setTranslateY(y);
		posX = x;
		posY = y;
		posZ = z;
		// System.out.println("Ball moveTo(" + x + "," + y + "," + z + ") Layout(" + this.getLayoutX() + "," + this.getLayoutY() + ") Translate(" + this.getTranslateX() + "," + this.getTranslateY());
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
	public void updateEdgesDisplay()
	{
		contentArea.updateEdgesDisplay();
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
			material.setDiffuseColor(Settings.START_COLOR);
		} else
		{
			material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		}
		isStartNode = isOrNot;
	}

	@Override
	public void setEndNode(boolean isOrNot)
	{
		if (isOrNot)
		{
			material.setDiffuseColor(Settings.END_COLOR);
		} else
		{
			material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		}
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

}

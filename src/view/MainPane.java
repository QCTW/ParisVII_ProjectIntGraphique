package view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.BaseNode;

public class MainPane extends Pane implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final Vector<BaseNode> vAllNodes = new Vector<BaseNode>();
	private final Vector<BaseNode> vGroupSelectedNodes = new Vector<BaseNode>();
	private final Vector<Edge> vDisplayLines = new Vector<Edge>();
	private final Vector<Connection> vConnections = new Vector<Connection>();
	private BaseNode connectFrom;
	private boolean isSelectMode = false;
	private ActionType action = ActionType.NONE;
	private Rectangle groupSelection = null;

	public MainPane()
	{
		super();
		this.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
			if (isSelectMode && action == ActionType.ADD_CONNECTION)
			{
				displayEdgesHintsTo(event.getX(), event.getY(), event.getZ());
			}
		});
	}

	public Cube createCube()
	{
		Cube c = new Cube(Settings.NODE_SIZE, this);
		this.getChildren().add(c);
		vAllNodes.add(c);
		return c;
	}

	public Ball createBall()
	{
		Ball b = new Ball(Settings.NODE_SIZE, this);
		this.getChildren().add(b);
		vAllNodes.add(b);
		return b;
	}

	public Vector<BaseNode> getAllNodes()
	{
		return vAllNodes;
	}

	public Vector<Connection> getAllConnection()
	{
		return vConnections;
	}

	public void displayEdgesHintsFrom(double x, double y, double z)
	{
		removeEdgesHints();
		for (BaseNode n : vAllNodes)
		{
			Edge line = new Edge(n.getPosX(), n.getPosY(), x, y);
			this.getChildren().add(line);
			vDisplayLines.add(line);
		}
	}

	public void displayEdgesHintsTo(double x, double y, double z)
	{
		removeEdgesHints();
		Edge line = new Edge(connectFrom.getPosX(), connectFrom.getPosY(), x, y);
		this.getChildren().add(line);
		vDisplayLines.add(line);
	}

	public void removeEdgesHints()
	{
		if (vDisplayLines.size() > 0)
		{
			for (Edge l : vDisplayLines)
			{
				this.getChildren().remove(l);
			}
			vDisplayLines.removeAllElements();
		}
	}

	public void startNodeSelectMode(BaseNode source)
	{
		connectFrom = source;
		isSelectMode = true;
		HashMap<Integer, BaseNode> hmRelatedNodes = new HashMap<Integer, BaseNode>();
		for (Connection conn : source.getEdges())
		{
			BaseNode start = conn.getStartPoint();
			BaseNode end = conn.getEndPoint();
			hmRelatedNodes.put(start.getNodeId(), start);
			hmRelatedNodes.put(end.getNodeId(), end);
		}

		for (BaseNode node : vAllNodes)
		{
			if (action == ActionType.ADD_CONNECTION)
			{
				hmRelatedNodes.remove(source.getNodeId());
				if (hmRelatedNodes.get(node.getNodeId()) != null)
				{
					node.setDisabled();
					for (Connection con : node.getEdges())
					{
						con.setDisabled();
					}
				} else
				{
					if (node != source)
						node.setSelectMode(isSelectMode);
				}

			} else
			{
				if (hmRelatedNodes.get(node.getNodeId()) == null)
				{
					node.setDisabled();
					for (Connection con : node.getEdges())
					{
						con.setDisabled();
					}
				} else
				{
					if (node != source)
						node.setSelectMode(isSelectMode);
				}
			}
		}
	}

	private void renableAllObjects()
	{
		for (BaseNode node : vAllNodes)
		{
			node.setSelectMode(isSelectMode);
			node.setEnabled();
			for (Connection oneConn : node.getEdges())
			{
				oneConn.setEnabled();
			}
		}
	}

	public void stopNodeSelectMode(BaseNode source)
	{
		isSelectMode = false;
		Connection conn = new Connection(connectFrom, source);
		if (action == ActionType.ADD_CONNECTION)
		{
			conn.initGraphic();
			vConnections.add(conn);
			this.getChildren().add(conn);
		} else
		{
			conn.delete();
			int nFound = vConnections.indexOf(conn);
			this.getChildren().remove(vConnections.get(nFound));
			vConnections.remove(conn);
		}
		renableAllObjects();
		source.removeSelected();
		connectFrom.removeSelected();
		connectFrom.resetSelectedStartingNode();
		action = ActionType.NONE;
	}

	public boolean isNodeSelectMode()
	{
		return isSelectMode;
	}

	public void updateEdgesDisplay()
	{
		for (Connection conn : vConnections)
		{
			conn.moveTo(conn.getEndPoint().getPoint3D(), conn.getStartPoint().getPoint3D());
			conn.toBack();
		}
	}

	public void deleteNode(BaseNode node)
	{
		Vector<Connection> conn2Remove = new Vector<Connection>();
		for (Connection conn : vConnections)
		{
			if (conn.getEndPoint().getNodeId() == node.getNodeId() || conn.getStartPoint().getNodeId() == node.getNodeId())
			{
				conn2Remove.add(conn);
			}
		}

		for (Connection conn : conn2Remove)
		{
			conn.delete();
			vConnections.remove(conn);
			this.getChildren().remove(conn);
		}

		vAllNodes.remove(node);
		this.getChildren().remove(node);
	}

	public void setAction(ActionType atype)
	{
		action = atype;
	}

	public void resetOtherStartNodes(BaseNode node)
	{
		for (BaseNode one : vAllNodes)
		{
			if (one.getNodeId() != node.getNodeId())
				one.setStartNode(false);
		}
	}

	public void resetOtherEndNodes(BaseNode node)
	{
		for (BaseNode one : vAllNodes)
		{
			if (one.getNodeId() != node.getNodeId())
				one.setEndNode(false);
		}
	}

	public void displayGroupSelection(double clickedX, double clickedY, double currentX, double currentY)
	{
		if (groupSelection == null)
		{
			groupSelection = new Rectangle();
			groupSelection.setArcWidth(1);
			groupSelection.setArcHeight(5);
			groupSelection.setStroke(Settings.SPECULAR_COLOR);
			groupSelection.setFill(null);
			groupSelection.setStrokeWidth(1);
		} else
		{
			this.getChildren().remove(groupSelection);
		}

		Point3D pointClicked = new Point3D(clickedX, clickedY, 0);
		// Point3D pointCurrent = new Point3D(currentX, currentY, 0);
		Point3D convertClicked = this.sceneToLocal(pointClicked);
		groupSelection.setX(convertClicked.getX());
		groupSelection.setY(convertClicked.getY());
		groupSelection.setWidth(currentX - clickedX);
		groupSelection.setHeight(currentY - clickedY);
		this.getChildren().add(groupSelection);

	}

	public void removeSelectedGroup()
	{
		for (BaseNode node : vAllNodes)
		{
			node.removeSelected();
		}
		vGroupSelectedNodes.clear();
	}

	public void detectSelectedNodes(double clickedX, double clickedY, double currentX, double currentY)
	{
		for (BaseNode node : vAllNodes)
		{
			Point3D p = new Point3D(node.getPosX(), node.getPosY(), node.getPosZ());
			Point3D pScene = this.localToScene(p);
			if (pScene.getX() > clickedX && pScene.getX() < currentX && pScene.getY() > clickedY && pScene.getY() < currentY)
			{
				addSelected(node);
			}
		}
		this.getChildren().remove(groupSelection);
	}

	public void addSelected(BaseNode nodeToAdd)
	{
		if (!vGroupSelectedNodes.contains(nodeToAdd))
		{
			vGroupSelectedNodes.add(nodeToAdd);
			nodeToAdd.displaySelected();
		}
	}

	public void moveSelectedNodes(double deltaX, double deltaY)
	{
		for (BaseNode selected : vGroupSelectedNodes)
		{
			selected.moveTo(deltaX, deltaY, 0);
		}
		updateEdgesDisplay();
	}

}

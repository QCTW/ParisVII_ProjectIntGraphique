package view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.BaseNode;

public class MainPane extends Pane implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final Vector<BaseNode> vAllNodes = new Vector<BaseNode>();
	private final Vector<Edge> vDisplayLines = new Vector<Edge>();
	private final Vector<Connection> vConnections = new Vector<Connection>();
	private BaseNode connectFrom;
	private boolean isSelectMode = false;
	private ActionType action = ActionType.NONE;

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
		if (action == ActionType.ADD_CONNECTION)
		{
			for (BaseNode node : vAllNodes)
			{
				node.setSelectMode(isSelectMode);
			}
		} else
		{
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
				node.setSelectMode(isSelectMode);
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

	public void stopNodeSelectMode(BaseNode source)
	{
		isSelectMode = false;
		if (action == ActionType.ADD_CONNECTION)
		{
			Connection con = new Connection(connectFrom, source);
			con.initGraphic();
			vConnections.add(con);
			this.getChildren().add(con);
			con.toBack();
			for (BaseNode node : vAllNodes)
			{
				node.setSelectMode(isSelectMode);
			}

		} else
		{
			Connection conn = new Connection(connectFrom, source);
			conn.delete();
			int nFound = vConnections.indexOf(conn);
			System.out.println("Tring to remove connection:" + conn.hashCode() + "@" + nFound);
			this.getChildren().remove(vConnections.get(nFound));
			vConnections.remove(conn);
			for (BaseNode node : vAllNodes)
			{
				node.setSelectMode(isSelectMode);
				node.setEnabled();
				for (Connection con : node.getEdges())
				{
					con.setEnabled();
				}
			}
		}
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

}

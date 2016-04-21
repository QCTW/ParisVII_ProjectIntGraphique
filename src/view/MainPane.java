package view;

import java.io.Serializable;
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

	public MainPane()
	{
		super();
		this.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
			if (isSelectMode)
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
		for (BaseNode node : vAllNodes)
		{
			node.setSelectMode(isSelectMode);
		}
	}

	public void stopNodeSelectMode(BaseNode source)
	{
		Connection con = new Connection(connectFrom, source);
		vConnections.add(con);
		this.getChildren().add(con);
		con.toBack();
		isSelectMode = false;
		for (BaseNode node : vAllNodes)
		{
			node.setSelectMode(isSelectMode);
		}
		source.removeSelected();
		connectFrom.removeSelected();
		connectFrom.resetSelectedStartingNode();
	}

	public boolean isNodeSelectMode()
	{
		return isSelectMode;
	}

}

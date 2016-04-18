package view;

import java.io.Serializable;
import java.util.Vector;

import javafx.scene.layout.Pane;
import model.BaseNode;

public class MainPane extends Pane implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final Vector<BaseNode> vAllNodes = new Vector<BaseNode>();
	private final Vector<Edge> vDisplayLines = new Vector<Edge>();

	public MainPane()
	{
		super();
	}

	public Cube createCube()
	{
		Cube c = new Cube(Settings.NODE_SIZE);
		this.getChildren().add(c);
		vAllNodes.add(c);
		return c;

	}

	public Ball createBall()
	{
		Ball b = new Ball(Settings.NODE_SIZE);
		this.getChildren().add(b);
		vAllNodes.add(b);
		return b;

	}

	public Vector<BaseNode> getAllNodes()
	{
		return vAllNodes;
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

}

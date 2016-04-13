package view;

import java.io.Serializable;
import java.util.Vector;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import model.BaseNode;

public class MainPane extends Pane implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final Vector<BaseNode> vAllNodes = new Vector<BaseNode>();
	private final Vector<Line> vDisplayLines = new Vector<Line>();

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

	public void displayAllEdgesFrom(double x, double y, double z)
	{
		if (vDisplayLines.size() > 0)
		{
			for (Line l : vDisplayLines)
			{
				this.getChildren().remove(l);
			}
			vDisplayLines.removeAllElements();
		}

		for (BaseNode n : vAllNodes)
		{
			Line line = new Line(n.getPosX(), n.getPosY(), x, y);
			line.setStroke(Settings.SPECULAR_COLOR);
			line.setStrokeWidth(2);
			this.getChildren().add(line);
			vDisplayLines.add(line);
		}
	}

}

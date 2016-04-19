package view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import model.BaseEdge;
import model.BaseNode;

public class Edge extends Group implements BaseEdge
{
	private static final long serialVersionUID = 1L;
	private Line line;
	private Label label;
	private BaseNode startNode, endNode;

	public Edge(double startX, double startY, double endX, double endY)
	{
		super();
		init(startX, startY, endX, endY);
	}

	public Edge(BaseNode nodeStart, BaseNode nodeEnd)
	{
		super();
		startNode = nodeStart;
		endNode = nodeEnd;
		init(startNode.getPosX(), startNode.getPosY(), endNode.getPosX(), endNode.getPosY());
	}

	private void init(double startX, double startY, double endX, double endY)
	{
		line = new Line(startX, startY, endX, endY);
		line.setStroke(Settings.SPECULAR_COLOR);
		line.setStrokeWidth(2);
		double dest = calculateDestination(startX, startY, endX, endY);

		label = new Label(convertToDisplayNumber(dest));
		this.getChildren().add(line);
		this.getChildren().add(label);

		label.relocate(middle(startX, endX), middle(startY, endY));
	}

	private double middle(double start, double end)
	{
		return (end + start) / 2;
	}

	private double calculateDestination(double startX, double startY, double endX, double endY)
	{
		return Math.sqrt(Math.pow((endX - startX), 2) + Math.pow((endY - startY), 2));
	}

	private String convertToDisplayNumber(double dest)
	{
		int nDest = (int) dest;
		return Integer.toString(nDest);
	}

	@Override
	public BaseNode getStartPoint()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseNode getEndPoint()
	{
		// TODO Auto-generated method stub
		return null;
	}

}

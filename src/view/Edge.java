package view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class Edge extends Group
{
	private Line line;
	private Label label;

	public Edge(double startX, double startY, double endX, double endY)
	{
		super();
		init(startX, startY, endX, endY);
	}

	private void init(double startX, double startY, double endX, double endY)
	{
		line = new Line(startX, startY, endX, endY);
		line.setStroke(Settings.SPECULAR_COLOR);
		line.setStrokeWidth(2);
		double dest = Utility.calculateDestination(startX, startY, endX, endY);

		label = new Label(convertToDisplayNumber(dest));
		this.getChildren().add(line);
		this.getChildren().add(label);

		label.relocate(middle(startX, endX), middle(startY, endY));
	}

	private double middle(double start, double end)
	{
		return (end + start) / 2;
	}

	private String convertToDisplayNumber(double dest)
	{
		int nDest = (int) dest;
		return Integer.toString(nDest);
	}

}

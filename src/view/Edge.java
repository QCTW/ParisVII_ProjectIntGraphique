package view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class Edge extends Group
{
	Line line;
	Label label;

	public Edge(double startX, double startY, double endX, double endY)
	{
		super();
		line = new Line(startX, startY, endX, endY);
		line.setStroke(Settings.SPECULAR_COLOR);
		line.setStrokeWidth(2);
		double dest = calculateDestination(startX, startY, endX, endY);
		label = new Label("Dest:" + Double.toString(dest));
		this.getChildren().add(line);
		this.getChildren().add(label);
	}

	private double calculateDestination(double startX, double startY, double endX, double endY)
	{
		return Math.sqrt(Math.pow((endX - startX), 2) + Math.pow((endY - startY), 2));
	}

}

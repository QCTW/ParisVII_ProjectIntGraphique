package view;

import java.io.Serializable;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Cylinder;
import model.BaseNode;

public class Connection extends Group implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final BaseNode nodeFrom, nodeTo;
	private final Label label;
	private final Cylinder bond;

	public Connection(BaseNode from, BaseNode to)
	{
		nodeFrom = from;
		nodeTo = to;
		double dest = Utility.calculateDestination(nodeFrom.getPosX(), nodeFrom.getPosY(), nodeTo.getPosX(), nodeTo.getPosY());
		bond = new Cylinder(5, dest);
		this.getChildren().add(bond);
		label = new Label();
		this.getChildren().add(label);
	}

}

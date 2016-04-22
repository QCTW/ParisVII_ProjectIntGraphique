package view;

import java.io.Serializable;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import model.BaseEdge;
import model.BaseNode;

public class Connection extends Group implements Serializable, BaseEdge
{
	private static final long serialVersionUID = 1L;
	private final BaseNode nodeFrom, nodeTo;
	private final Label label;
	private final Cylinder bond;

	public Connection(BaseNode from, BaseNode to)
	{
		nodeFrom = from;
		nodeTo = to;

		Point3D diff = to.getPoint3D().subtract(from.getPoint3D());
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Settings.SPECULAR_COLOR);

		bond = new Cylinder(5, diff.magnitude());
		bond.setMaterial(material);
		this.getChildren().add(bond);

		label = new Label(String.valueOf((int) diff.magnitude()));
		this.getChildren().add(label);

		moveTo(to.getPoint3D(), from.getPoint3D());

		nodeFrom.addEdge(this);
		nodeTo.addEdge(this);
	}

	public void moveTo(Point3D to, Point3D from)
	{
		Point3D mid = to.midpoint(from);
		Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());
		Point3D diff = to.subtract(from);
		Point3D axisOfRotation = diff.crossProduct(Rotate.Y_AXIS);
		double angle = Math.acos(diff.normalize().dotProduct(Rotate.Y_AXIS));
		Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);
		bond.getTransforms().clear();
		bond.setHeight(diff.magnitude());
		bond.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

		label.getTransforms().clear();
		label.getTransforms().add(moveToMidpoint);
		label.setText(String.valueOf((int) diff.magnitude()));
	}

	@Override
	public BaseNode getStartPoint()
	{
		return nodeFrom;
	}

	@Override
	public BaseNode getEndPoint()
	{
		return nodeTo;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodeFrom == null) ? 0 : nodeFrom.getNodeId());
		result = prime * result + ((nodeTo == null) ? 0 : nodeTo.getNodeId());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connection other = (Connection) obj;
		if (nodeFrom == null)
		{
			if (other.nodeFrom != null)
				return false;
		} else if (nodeFrom.getNodeId() != other.nodeFrom.getNodeId())
			return false;
		if (nodeTo == null)
		{
			if (other.nodeTo != null)
				return false;
		} else if (nodeTo.getNodeId() != other.nodeTo.getNodeId())
			return false;
		return true;
	}

}

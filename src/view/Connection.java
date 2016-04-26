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
	@Override
	public String toString()
	{
		return "Connection " + nodeFrom.getNodeLabel() + " <-> " + nodeTo.getNodeLabel() + " (length:" + label.getText() + ")";
	}

	private static final long serialVersionUID = 1L;
	private final BaseNode nodeFrom, nodeTo;
	private Label label;
	private Cylinder bond;
	private int weight;

	public Connection(BaseNode from, BaseNode to)
	{
		nodeFrom = from;
		nodeTo = to;
	}

	public void initGraphic()
	{
		Point3D diff = nodeTo.getPoint3D().subtract(nodeFrom.getPoint3D());
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Settings.SPECULAR_COLOR);

		bond = new Cylinder(5, diff.magnitude());
		bond.setMaterial(material);
		this.getChildren().add(bond);

		label = new Label();
		this.getChildren().add(label);

		moveTo(nodeTo.getPoint3D(), nodeFrom.getPoint3D());

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

		weight = (int) diff.magnitude();
		label.getTransforms().clear();
		label.getTransforms().add(moveToMidpoint);
		label.setText(String.valueOf(weight));
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
		// final int prime = 31;
		// int result = 1;
		// result = prime * result + ((nodeFrom == null) ? 0 : nodeFrom.getNodeId());
		// result = prime * result + ((nodeTo == null) ? 0 : nodeTo.getNodeId());
		int result = 1;
		String strFrom = String.valueOf((nodeFrom == null) ? 0 : nodeFrom.getNodeId());
		String strTo = String.valueOf((nodeTo == null) ? 0 : nodeTo.getNodeId());
		for (char c : strFrom.toCharArray())
		{
			result += c;
		}
		for (char c : strTo.toCharArray())
		{
			result += c;
		}
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
		} else if (nodeFrom.getNodeId() != other.nodeFrom.getNodeId() && nodeFrom.getNodeId() != other.nodeTo.getNodeId())
			return false;
		if (nodeTo == null)
		{
			if (other.nodeTo != null)
				return false;
		} else if (nodeTo.getNodeId() != other.nodeTo.getNodeId() && nodeTo.getNodeId() != other.nodeFrom.getNodeId())
			return false;
		return true;
	}

	@Override
	public void delete()
	{
		nodeFrom.removeEdge(this);
		nodeTo.removeEdge(this);
		this.getChildren().clear();
	}

	@Override
	public void setDisabled()
	{
		this.setEffect(Settings.DISABLE_EFFECT);
	}

	@Override
	public void setEnabled()
	{
		this.setEffect(null);
	}

	@Override
	public int getWeight()
	{
		return weight;
	}

}

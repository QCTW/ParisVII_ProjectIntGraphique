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

}

package view;

import java.io.Serializable;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
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

		double dest = Utility.calculateDestination(nodeFrom.getFXNode().getTranslateX(), nodeFrom.getFXNode().getTranslateY(), nodeTo.getFXNode().getTranslateX(), nodeTo.getFXNode().getTranslateY());
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Settings.SPECULAR_COLOR);

		Point3D mid = to.getPoint3D().midpoint(from.getPoint3D());
		Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

		Point3D diff = to.getPoint3D().subtract(from.getPoint3D());
		Point3D axisOfRotation = diff.crossProduct(Rotate.Y_AXIS);
		double angle = Math.acos(diff.normalize().dotProduct(Rotate.Y_AXIS));
		Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

		bond = new Cylinder(5, dest);
		bond.setMaterial(material);
		bond.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
		this.getChildren().add(bond);

		label = new Label(String.valueOf((int) dest));
		this.getChildren().add(label);
		label.getTransforms().add(moveToMidpoint);
	}

}

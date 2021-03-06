package view;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import model.Edge;

public class ViewableEdge extends Edge
{
	private static final long serialVersionUID = 1L;
	private final Group group = new Group();
	PhongMaterial material = new PhongMaterial();
	private final Color originalColor = Settings.DIFFUSE_COLOR;
	private Label label;
	private Cylinder bond;

	public ViewableEdge(ViewableNode from, ViewableNode to)
	{
		super(from, to);
	}

	public void initGraphic(ViewableNode from, ViewableNode to)
	{
		Point3D diff = from.getPoint3D().subtract(to.getPoint3D());

		material.setDiffuseColor(originalColor);
		material.setSpecularColor(Settings.SPECULAR_COLOR);

		bond = new Cylinder(5, diff.magnitude());
		bond.setMaterial(material);
		group.getChildren().add(bond);

		label = new Label();
		label.getStyleClass().add("value-label-edge");
		group.getChildren().add(label);

		moveTo(to.getPoint3D(), from.getPoint3D());
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

		this.setWeight((int) diff.magnitude());
		label.getTransforms().clear();
		label.getTransforms().add(moveToMidpoint);
		label.setText(String.valueOf(this.getWeight()));
	}

	@Override
	public void delete()
	{
		super.delete();
		group.getChildren().clear();
	}

	public void setDisabled()
	{
		group.setEffect(Settings.DISABLE_EFFECT);
		group.toBack();
	}

	public void setEnabled()
	{
		group.setEffect(null);
		group.toBack();
	}

	public Group getFXNode()
	{
		return group;
	}

	public void setAlgoShortest(boolean isOrNot)
	{
		if (isOrNot)
		{
			material.setDiffuseColor(Settings.ALGO_SHORTEST_COLOR);
			label.setTextFill(Settings.ALGO_SHORTEST_COLOR);
		} else
		{
			material.setDiffuseColor(originalColor);
			label.setTextFill(originalColor);
		}
	}

	public void setAlgoSource(boolean isOrNot)
	{
		if (isOrNot)
		{
			material.setDiffuseColor(Settings.ALGO_SRC_COLOR);
			label.setTextFill(Settings.ALGO_SRC_COLOR);
		} else
		{
			material.setDiffuseColor(originalColor);
			label.setTextFill(originalColor);
		}
	}

	public void resetStatus()
	{
		setAlgoSource(false);
		setAlgoShortest(false);
	}

}

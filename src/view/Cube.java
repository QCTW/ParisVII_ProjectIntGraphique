package view;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

public class Cube extends Box
{

	public Cube()
	{
		super(Settings.NODE_SIZE, Settings.NODE_SIZE, Settings.NODE_SIZE);
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Color.WHITE);
		this.setMaterial(material);
		this.getTransforms().add(new Rotate(45, 0, 0, 0, Rotate.X_AXIS));
		this.getTransforms().add(new Rotate(45, 0, 0, 0, Rotate.Y_AXIS));
	}

}

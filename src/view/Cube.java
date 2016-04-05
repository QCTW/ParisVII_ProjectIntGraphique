package view;

import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Cube extends Box
{

	public Cube()
	{
		super(Settings.NODE_SIZE, Settings.NODE_SIZE, Settings.NODE_SIZE);
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Settings.SPECULAR_COLOR);
		this.setMaterial(material);
		// this.getTransforms().add(new Rotate(30, 0, 0, 0, Rotate.X_AXIS));
		// this.getTransforms().add(new Rotate(30, 0, 0, 0, Rotate.Y_AXIS));
		// this.setCullFace(CullFace.NONE);
		// this.setDrawMode(DrawMode.LINE);
	}

}

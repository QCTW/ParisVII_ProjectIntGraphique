package view;

import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Ball extends Sphere
{

	public Ball()
	{
		super(Settings.NODE_SIZE / 2);
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Settings.SPECULAR_COLOR);
		this.setMaterial(material);
	}

}

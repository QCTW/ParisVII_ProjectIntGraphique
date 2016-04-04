package view;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Cube extends Box
{

	public Cube()
	{
		super(Settings.NODE_SIZE, Settings.NODE_SIZE, Settings.NODE_SIZE);
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Settings.DIFFUSE_COLOR);
		material.setSpecularColor(Color.WHITE);
		this.setMaterial(material);
	}

}

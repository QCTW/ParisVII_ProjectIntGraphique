package view;

import javafx.scene.shape.Circle;

public class SelectedRing extends Circle
{
	public SelectedRing(double radious)
	{
		super(radious);
		this.setStroke(Settings.SPECULAR_COLOR);
		this.setFill(null);
		this.setStrokeWidth(3);
	}

}

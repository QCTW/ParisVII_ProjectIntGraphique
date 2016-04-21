package view;

import javafx.scene.shape.Circle;

public class SelectedRing extends Circle
{
	public SelectedRing(double radious)
	{
		super(radious / 1.5 + 5);
		this.setStroke(Settings.SPECULAR_COLOR);
		this.setFill(null);
		this.setStrokeWidth(3);
	}

}

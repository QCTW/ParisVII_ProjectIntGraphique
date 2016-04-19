package view;

import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class SelectedRing extends Path
{

	public SelectedRing(double posX, double posY, double radious)
	{
		super();
		MoveTo moveTo = new MoveTo();
		moveTo.setX(posX);
		moveTo.setY(posY);

		ArcTo arcTo = new ArcTo();
		arcTo.setX(posX);
		arcTo.setY(posY);
		arcTo.setRadiusX(radious);
		arcTo.setRadiusY(radious);

		this.getElements().add(moveTo);
		this.getElements().add(arcTo);
	}

}

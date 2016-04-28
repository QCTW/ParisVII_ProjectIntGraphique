package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class StepBackButton extends Button
{

	public StepBackButton()
	{
		super();
		Icon iconControl = new Icon(Settings.IMAGE_BSTEP_BACKWARD);
		this.setTooltip(new Tooltip("Play one step forward"));
		this.setGraphic(iconControl);
		this.focusTraversableProperty().setValue(false);
	}

}

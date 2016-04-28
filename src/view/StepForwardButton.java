package view;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class StepForwardButton extends Button
{

	public StepForwardButton()
	{
		super();
		Icon iconControl = new Icon(Settings.IMAGE_BSTEP_FORWARD);
		this.setTooltip(new Tooltip("Play one step back"));
		this.setGraphic(iconControl);
		this.focusTraversableProperty().setValue(false);
	}

}
